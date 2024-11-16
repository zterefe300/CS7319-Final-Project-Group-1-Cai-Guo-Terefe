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
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.*;

@RestController
@RequestMapping("/api/item")
public class ItemController {
    @Autowired
    private Dao dao;
    @Autowired
    private JavaMailSender mailSender;

    @GetMapping
    public ResponseEntity<List<ItemResponse>> getAllItems() {
        return ResponseEntity.ok(dao.getAllItems().stream().map(this::mapItemToItemResponse).toList());
    }

    @GetMapping("/{limit}")
    public ResponseEntity<List<ItemResponse>> getItemsWithLimit(@PathVariable Integer limit) {
        return ResponseEntity.ok(dao.selectLimit(limit).stream().map(this::mapItemToItemResponse).toList());
    }

    @PostMapping
    public ResponseEntity<ItemResponse> createItem(@RequestBody ItemRequest itemRequest) {
        final Integer vendorId = itemRequest.vendorId();
        final MultipartFile pictureStream = itemRequest.pictureStream();
        final String itemPictureRootUrl = PictureUploadUtil.uploadPicture(pictureStream, "");

        final Integer quantity = 1;
        final Integer itemId = dao.createItem(mapItemRequestToItem(itemRequest, itemPictureRootUrl, vendorId));
         dao.createStockRecord(StockRecord.builder().itemId(itemId)
                .quantity(quantity).effectiveDate(Date.from(Instant.now())).build());
        return ResponseEntity.ok(mapItemToItemResponseWithQuantity(dao.getItem(itemId), quantity));
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<ItemResponse> updateItem(@PathVariable Integer itemId, @RequestBody ItemRequest itemRequest) {
        final Integer vendorId = itemRequest.vendorId();
        final MultipartFile pictureStream = itemRequest.pictureStream();
        final String itemPictureRootUrl = PictureUploadUtil.uploadPicture(pictureStream, "");

        dao.updateItem(mapItemRequestToItem(itemRequest, itemPictureRootUrl, vendorId));
        dao.updateStockRecord(StockRecord.builder()
                .itemId(itemId).quantity(itemRequest.quantity())
                .effectiveDate(Date.from(Instant.now())).build());

        return ResponseEntity.ok(mapItemToItemResponseWithQuantity(dao.getItem(itemId),
                itemRequest.quantity()));
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@PathVariable Integer itemId) {
        dao.deleteItem(itemId);
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
        lowStockItems.forEach(item -> {
            final Vendor vendor = dao.getVendor(item.getVendorId());

            try {
                if (vendor.getEmail() != null) {
                    NotificationUtil.sendEmail(mailSender, vendor.getEmail(), String.format("Item: %s, reorder", item.getName()),
                            NotificationUtil.createItemReorderEmailBody(vendor.getName(), item.getName(), item.getReorderQuantity()));
                }

                if (vendor.getPhone() != null) {
                    NotificationUtil.sendSMS(vendor.getPhone(),
                            NotificationUtil.createItemReorderSMSBody(item.getName(), item.getReorderQuantity()));
                }
                //TODO: update the status to pass the string instead of integer
                dao.createReorderTracker(new ReorderTracker(item.getId(), Integer.parseInt(ReorderStatus.REORDERED.name()) , Date.from(Instant.now()), vendor.getId(), ""));
                successfullyReorderedItems.add(new ReorderTrackerResponse(item.getId(), item.getVendorId(), ReorderStatus.REORDERED, ""));
            } catch (Exception e) {
                //TODO: update the status to pass the string instead of integer
                dao.createReorderTracker(new ReorderTracker(item.getId(), Integer.parseInt(ReorderStatus.FAILED.name()) , Date.from(Instant.now()), vendor.getId(), e.getMessage()));
                itemsFailedToReorder.add(new ReorderTrackerResponse(item.getId(), item.getVendorId(), ReorderStatus.FAILED, e.getMessage()));
            }
        });

        return new ReorderTrackerResponseWrapper(successfullyReorderedItems, itemsFailedToReorder);
    }

    private ItemResponse mapItemToItemResponse(Item item) {
        final Optional<StockRecord> recentStockRecord = dao.findByItemId(item.getId())
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

    private Item mapItemRequestToItem(ItemRequest itemRequest,
                                      final String itemPicturesRootUrl, final Integer vendorId) {
        Item.ItemBuilder itemBuilder = Item.builder()
                .name(itemRequest.name())
                .detail(itemRequest.detail())
                .alarmThreshold(itemRequest.quantityAlarmThreshold())
                .quantityThreshold(itemRequest.quantityReorderThreshold())
                .vendorId(vendorId).effectiveDate(Date.from(Instant.now()));
        if (itemPicturesRootUrl != null) {
            itemBuilder.pics(itemPicturesRootUrl);
        }

        return itemBuilder.build();
    }
}
