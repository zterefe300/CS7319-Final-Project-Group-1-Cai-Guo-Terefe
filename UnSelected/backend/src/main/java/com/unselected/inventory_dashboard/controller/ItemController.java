package com.unselected.inventory_dashboard.controller;

import com.unselected.inventory_dashboard.constants.ReorderStatus;
import com.unselected.inventory_dashboard.db.Dao;
import com.unselected.inventory_dashboard.dto.*;
import com.unselected.inventory_dashboard.entity.Item;
import com.unselected.inventory_dashboard.entity.ReorderTracker;
import com.unselected.inventory_dashboard.entity.StockRecord;
import com.unselected.inventory_dashboard.entity.Vendor;
import com.unselected.inventory_dashboard.utils.NotificationUtil;
import com.unselected.inventory_dashboard.utils.PictureUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/item")
public class ItemController {
    @Autowired
    private Dao dao;
    @Autowired
    private JavaMailSender mailSender;
    @Value("${email.mode}")
    private String emailMode;
    @Value("${sms.mode}")
    private String smsMode;

    @GetMapping
    public ResponseEntity<List<ItemResponse>> getAllItems() {
        return ResponseEntity.ok(dao.getAllItems().stream().map(this::mapItemToItemResponse).toList());
    }

    @GetMapping("/{limit}")
    public ResponseEntity<List<ItemResponse>> getItemsWithLimit(@PathVariable Integer limit) {
        return ResponseEntity.ok(dao.selectItemWithLimit(limit).stream().map(this::mapItemToItemResponse).toList());
    }

    @GetMapping("/getReorderTrackerData")
    public ResponseEntity<List<ReorderTrackerResponse>> getReorderTrackerData() {
        return ResponseEntity.ok(dao.getAllReorderTrackers()
                .stream().collect(Collectors.groupingBy(ReorderTracker::getItemId, Collectors.maxBy(Comparator.comparing(ReorderTracker::getDate))))
                .values().stream().map(Optional::orElseThrow).toList().stream().map(this::mapReorderTrackerToReorderTrackerResponse).toList());
    }

    @PostMapping
    public ResponseEntity<ItemResponse> createItem(@RequestBody ItemRequest itemRequest) {
        final Integer vendorId = itemRequest.vendorId();
        final String pictureBase64 = itemRequest.pictureBase64();
        final String itemPictureRootUrl = PictureUploadUtil.uploadPicture(pictureBase64,
                PictureUploadUtil.createItemPictureName(itemRequest.name(), itemRequest.vendorId()));

        final Integer quantity = 1;
        final Integer itemId = dao.createItem(mapItemRequestToItem(itemRequest, itemPictureRootUrl, vendorId));
         dao.createStockRecord(StockRecord.builder().itemId(itemId)
                .quantity(quantity).effectiveDate(Date.from(Instant.now())).build());
        return ResponseEntity.ok(mapItemToItemResponseWithQuantity(dao.getItem(itemId), quantity));
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<ItemResponse> updateItem(@PathVariable Integer itemId, @RequestBody ItemRequest itemRequest) {
        final Integer vendorId = itemRequest.vendorId();
        final String pictureBase64 = itemRequest.pictureBase64();
        final String itemPictureRootUrl = PictureUploadUtil.uploadPicture(pictureBase64,
                PictureUploadUtil.createItemPictureName(itemRequest.name(), itemRequest.vendorId()));

        dao.updateItem(mapItemRequestToItem(itemRequest, itemPictureRootUrl, vendorId, itemId));
        dao.createStockRecord(StockRecord.builder()
                .itemId(itemId).quantity(itemRequest.quantity())
                .effectiveDate(Date.from(Instant.now())).build());
        return ResponseEntity.ok(mapItemToItemResponseWithQuantity(dao.getItem(itemId),
                itemRequest.quantity()));
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@PathVariable Integer itemId) {
        dao.deleteItem(itemId);
    }

    @PutMapping("/fulfillReorder")
    public ReorderTrackerResponse fulfillItemReorder(@RequestBody final ReorderTrackerRequest reorderTrackerRequest) {
        if (!reorderTrackerRequest.status().equals(ReorderStatus.REORDERED.name())) {
            throw new RuntimeException("Can't fulfill order that is not in reorder status");
        }

        final Item item = dao.getItem(reorderTrackerRequest.itemId());
        final Integer reorderQuantity = Optional.ofNullable(item
                .getReorderQuantity()).orElse(0);

        final ReorderTracker reorderTracker = dao.getReorderTracker(reorderTrackerRequest.itemId(),
                reorderTrackerRequest.date(), BigInteger.ONE.intValue());

        final Optional<StockRecord> currentStockRecord = dao.findStockRecordByItemId(reorderTrackerRequest.itemId())
                .stream().max(Comparator.comparing(StockRecord::getEffectiveDate));
        final Integer recentStockRecordQuantity = currentStockRecord.map(StockRecord::getQuantity).orElse(null);

        final Integer newTotalQuantity = recentStockRecordQuantity != null ?
                recentStockRecordQuantity + reorderQuantity : reorderQuantity;
        //update the stock
        dao.createStockRecord((StockRecord.builder()
                .itemId(reorderTrackerRequest.itemId()).quantity(newTotalQuantity)
                .effectiveDate(Date.from(Instant.now())).build()));
        //update the reorder tracker with fulfilled status
        Date dateNow = Date.from(Instant.now());
        dao.createReorderTracker(new ReorderTracker(reorderTracker.getItemId(), BigInteger.TWO.intValue(), dateNow, reorderTracker.getVendorId(), ""));
        return new ReorderTrackerResponse(reorderTracker.getItemId(), item.getName(), reorderTracker.getVendorId(), ReorderStatus.FULFILLED.name(), "", dateNow);
    }

    @PutMapping("/reorder")
    public ResponseEntity<ReorderTrackerResponseWrapper> reorderLowStockItems() {
        return ResponseEntity.ok(reorderItems());
    }

    @Scheduled(cron = "0 0 2 * * ?")
    ReorderTrackerResponseWrapper reorderItems() {
        final List<ReorderTrackerResponse> successfullyReorderedItems = new ArrayList<>();
        final List<ReorderTrackerResponse> itemsFailedToReorder = new ArrayList<>();

        final List<ItemAndQty> lowStockItems = dao.findAllBelowQtyThreshold();
        lowStockItems.forEach(itemAndQty -> {
            final Vendor vendor = dao.getVendor(itemAndQty.getVendorId());

            try {
                handleSendingNotificationForReorder(vendor, mapItemQtyToItem(itemAndQty));
                Date dateNow = Date.from(Instant.now());
                dao.createReorderTracker(new ReorderTracker(itemAndQty.getId(), BigInteger.ONE.intValue(), dateNow, vendor.getId(), ""));
                successfullyReorderedItems.add(new ReorderTrackerResponse(itemAndQty.getId(), itemAndQty.getName(), itemAndQty.getVendorId(), ReorderStatus.REORDERED.name(), "", dateNow));
            } catch (Exception e) {
                Date dateNow = Date.from(Instant.now());
                dao.createReorderTracker(new ReorderTracker(itemAndQty.getId(), BigInteger.ZERO.intValue(), dateNow, vendor.getId(), e.getMessage()));
                itemsFailedToReorder.add(new ReorderTrackerResponse(itemAndQty.getId(), itemAndQty.getName(), itemAndQty.getVendorId(), ReorderStatus.FAILED.name(), e.getMessage(), dateNow));
            }
        });

        return new ReorderTrackerResponseWrapper(successfullyReorderedItems, itemsFailedToReorder);
    }

    @Scheduled(fixedRate = 3000)
    //TODO: Update cron to drive its value from application properties
    public void sendAlarmForItemsBelowAlarmThreshold() {
        final List<ItemAndQty> lowStockItems = dao.findAllBelowAlarmThreshold();
        lowStockItems.forEach(itemAndQty -> handleSendingNotificationForAlarm(dao.getVendor(itemAndQty.getId()), mapItemQtyToItem(itemAndQty)));
    }

    private void handleSendingNotificationForAlarm(final Vendor vendor, final Item item) {
        if (vendor.getEmail() != null) {
            NotificationUtil.sendEmail(mailSender, vendor.getEmail(), String.format("Item: %s, alarm", item.getName()),
                    NotificationUtil.createItemAlarmEmailBody(vendor.getName(), item.getName()), emailMode);
        }

        if (vendor.getPhone() != null) {
            NotificationUtil.sendSMS(vendor.getPhone(),
                    NotificationUtil.createItemAlarmSMSBody(item.getName()), smsMode);
        }
    }

    private void handleSendingNotificationForReorder(final Vendor vendor, final Item item) {
        if (vendor.getEmail() != null) {
            NotificationUtil.sendEmail(mailSender, vendor.getEmail(), String.format("Item: %s, reorder", item.getName()),
                    NotificationUtil.createItemReorderEmailBody(vendor.getName(), item.getName(), item.getReorderQuantity()), emailMode);
        }

        if (vendor.getPhone() != null) {
            NotificationUtil.sendSMS(vendor.getPhone(),
                    NotificationUtil.createItemReorderSMSBody(item.getName(), item.getReorderQuantity()), smsMode);
        }
    }

    private ItemResponse mapItemToItemResponse(Item item) {
        final Optional<StockRecord> recentStockRecord = dao.findStockRecordByItemId(item.getId())
                .stream().max(Comparator.comparing(StockRecord::getEffectiveDate));
        final Integer quantity = recentStockRecord.map(StockRecord::getQuantity).orElse(null);
        return mapItemToItemResponseWithQuantity(item, quantity);
    }

    private ItemResponse mapItemToItemResponseWithQuantity(Item item, Integer quantity) {
        return new ItemResponse(
          item.getId(),
          item.getName(),
          item.getDetail(),
          item.getPics(),
          quantity,
          item.getAlarmThreshold(),
          item.getQuantityThreshold(),
          item.getVendorId()
        );
    }

    private Item mapItemRequestToItem(final ItemRequest itemRequest, final java.lang.String itemPicturesRootUrl, final Integer vendorId) {
        return mapItemRequestToItem(itemRequest, itemPicturesRootUrl, vendorId, null);
    }

    private Item mapItemRequestToItem(ItemRequest itemRequest,
                                      final java.lang.String itemPicturesRootUrl, final Integer vendorId, final Integer itemId) {
        Item.ItemBuilder itemBuilder = Item.builder()
                .name(itemRequest.name())
                .detail(itemRequest.detail())
                .alarmThreshold(itemRequest.quantityAlarmThreshold())
                .quantityThreshold(itemRequest.quantityReorderThreshold())
                .vendorId(vendorId).effectiveDate(Date.from(Instant.now()));
        if (itemPicturesRootUrl != null) {
            itemBuilder.pics(itemPicturesRootUrl);
        }

        if (itemId != null) {
            itemBuilder.id(itemId);
        }

        return itemBuilder.build();
    }

    private ReorderTrackerResponse mapReorderTrackerToReorderTrackerResponse(final ReorderTracker reorderTracker) {
        final String reorderStatus = reorderTracker.getStatus() == 1 ? ReorderStatus.REORDERED.name() : ReorderStatus.FAILED.name();
        return new ReorderTrackerResponse(reorderTracker.getItemId(), dao.getItem(reorderTracker.getItemId()).getName(), reorderTracker.getVendorId(),
                reorderStatus, reorderTracker.getErrorMessage(), reorderTracker.getDate());
    }

    private Item mapItemQtyToItem(final ItemAndQty itemAndQty) {
        return new Item(itemAndQty.getId(), itemAndQty.getName(), itemAndQty.getDetail(), itemAndQty.getPics(), itemAndQty.getAlarmThreshold(),
                itemAndQty.getQuantityThreshold(), itemAndQty.getVendorId(), itemAndQty.getEffectiveDate(), itemAndQty.getReorderQuantity());
    }
}
