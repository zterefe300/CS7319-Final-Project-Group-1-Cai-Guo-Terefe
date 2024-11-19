package com.selected.inventory_dashboard.service.impl;

import com.selected.inventory_dashboard.constants.ReorderStatus;
import com.selected.inventory_dashboard.dtovo.req.ItemRequest;
import com.selected.inventory_dashboard.dtovo.res.ItemAndQty;
import com.selected.inventory_dashboard.dtovo.res.ItemResponse;
import com.selected.inventory_dashboard.dtovo.res.ReorderTrackerResponse;
import com.selected.inventory_dashboard.dtovo.res.ReorderTrackerResponseWrapper;
import com.selected.inventory_dashboard.exception.AlarmThresholdException;
import com.selected.inventory_dashboard.exception.NoItemDataException;
import com.selected.inventory_dashboard.exception.NoVendorDataException;
import com.selected.inventory_dashboard.persistence.dao.ItemMapper;
import com.selected.inventory_dashboard.persistence.dao.ReorderTrackerMapper;
import com.selected.inventory_dashboard.persistence.dao.StockRecordMapper;
import com.selected.inventory_dashboard.persistence.dao.VendorMapper;
import com.selected.inventory_dashboard.persistence.entity.Item;
import com.selected.inventory_dashboard.persistence.entity.ReorderTracker;
import com.selected.inventory_dashboard.persistence.entity.StockRecord;
import com.selected.inventory_dashboard.persistence.entity.Vendor;
import com.selected.inventory_dashboard.service.interfaces.EmailService;
import com.selected.inventory_dashboard.service.interfaces.ItemService;
import com.selected.inventory_dashboard.service.interfaces.SMSService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
public class ItemServiceImpl implements ItemService {
    private static final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);

    private final ItemMapper itemMapper;
    private final StockRecordMapper stockRecordMapper;
    private final VendorMapper vendorMapper;
    private final FileUploaderServiceCoordinator fileUploaderServiceCoordinator;
    private final EmailService emailService;
    private final SMSService smsService;
    private final ReorderTrackerMapper reorderTrackerMapper;

    public ItemServiceImpl(final ItemMapper itemMapper, final StockRecordMapper stockRecordMapper, final VendorMapper vendorMapper, final FileUploaderServiceCoordinator fileUploaderServiceCoordinator,
                           final EmailService emailService, final SMSService smsService, ReorderTrackerMapper reorderTrackerMapper) {
        this.itemMapper = itemMapper;
        this.stockRecordMapper = stockRecordMapper;
        this.vendorMapper = vendorMapper;
        this.fileUploaderServiceCoordinator = fileUploaderServiceCoordinator;
        this.emailService = emailService;
        this.smsService = smsService;
        this.reorderTrackerMapper = reorderTrackerMapper;
    }

    @Override
    public List<ItemResponse> getAllItems() {
        //TODO: update this once we have  a join query between item and stock record
        return itemMapper.selectAll().stream().map(this::buildItemResponse).toList();
    }

    @Override
    public List<ItemResponse> getAllItemsWithLimit(final Integer limit) {
        return itemMapper.selectLimit(limit).stream().map(this::buildItemResponse).toList();
    }

    ///TODO: Breakdown service into methods. Add error handling for insert operations
    @Override
    public ItemResponse insertNewItem(final ItemRequest itemRequest) {
        //check if alert threshold is above re-order threshold
        validateThreshold(itemRequest);

        final Integer vendorId = itemRequest.vendorId();
        validateVendorData(vendorId);

        final String pictureBase64 = itemRequest.pictureBase64();
        final String itemPicturesRootUrl =  Optional.ofNullable(insertPictureAndGetUrl(pictureBase64,
                        createItemPictureName(itemRequest.name(), itemRequest.vendorId())))
                .orElse("");

        Integer itemId = itemMapper.insert(buildItemFromItemRequest(itemRequest, itemPicturesRootUrl, vendorId));
        final Item item = itemMapper.selectByPrimaryKey(itemId);

        final Integer quantity = 1;
        //Create stock record of the item with quantity one since we only add a single item.
        stockRecordMapper.insert(StockRecord.builder().itemId(itemId)
                .quantity(quantity).effectiveDate(Date.from(Instant.now())).build());
        return buildItemResponseGivenQuantity(item, quantity);
    }

    ///TODO: Breakdown service into methods. Add error handling for update operations
    @Override
    public ItemResponse updateItem(final Integer itemId, final ItemRequest itemRequest) {
        //check if alert threshold is above re-order threshold
        validateThreshold(itemRequest);

        if (itemMapper.selectByPrimaryKey(itemId) == null) {
            throw new NoItemDataException();
        }

        final Integer vendorId = itemRequest.vendorId();
        validateVendorData(vendorId);

        final String updatedPictureFileUrl = insertPictureAndGetUrl(itemRequest.pictureBase64(),
                createItemPictureName(itemRequest.name(), itemRequest.vendorId()));
        final Item itemWithUpdates = buildItemFromItemRequest(itemRequest, updatedPictureFileUrl, vendorId);

        //Update item
        itemMapper.updateByPrimaryKey(
                itemWithUpdates
        );
        //Update stock record
        stockRecordMapper.updateByPrimaryKey(StockRecord.builder()
                .itemId(itemId).quantity(itemRequest.quantity())
                .effectiveDate(Date.from(Instant.now())).build());

        final Item updatedItem = itemMapper.selectByPrimaryKey(itemId);
        return buildItemResponseGivenQuantity(updatedItem, itemRequest.quantity());
    }

    @Override
    public boolean deleteItem(final Integer itemId) {
        itemMapper.deleteByPrimaryKey(itemId);
        return itemMapper.selectByPrimaryKey(itemId) == null;
    }

    @Override
    @Scheduled(cron = "0 0 2 * * ?")
    //TODO: Update cron to drive its value from application properties
    public ReorderTrackerResponseWrapper reorderItemsLowStockItems() {
        final List<ReorderTrackerResponse> successfullyReorderedItems = new ArrayList<>();
        final List<ReorderTrackerResponse> itemsFailedToReorder = new ArrayList<>();
        Map<ReorderStatus, List<ReorderTrackerResponse>> statusListMap = Map.of(ReorderStatus.REORDERED, successfullyReorderedItems,
                ReorderStatus.FAILED, itemsFailedToReorder);

        final List<ItemAndQty> lowStockItems = itemMapper.findAllBelowQtyThreshold();
        lowStockItems.forEach(item -> {
            final ReorderTrackerResponse reorderTrackerResponse =  handleSingleItemReorder(item);
            statusListMap.get(reorderTrackerResponse.reorderStatus()).add(reorderTrackerResponse);
        });

        return new ReorderTrackerResponseWrapper(successfullyReorderedItems, itemsFailedToReorder);
    }

    @Override
    public boolean updateStock(int itemId, int quantity) {
        int update = stockRecordMapper.updateQuantity(itemId, quantity);
        return update>0;
    }

//    @Override
//    public List<ReorderTrackerResponse> getReorderTrackerData() {
//        return reorderTrackerMapper.selectAll();
//    }

    private void validateVendorData(final Integer vendorId) {
        if (vendorId == null) {
            throw new NoVendorDataException();
        }

        //Throw no vendor data found, when a vendor id is provided but the vendor doesn't exist in the db
        if (vendorMapper.selectByPrimaryKey(vendorId) == null) {
            throw NoVendorDataException.vendorNotFoundException();
        }
    }

    private void validateThreshold(final ItemRequest itemRequest) {
        if (itemRequest.quantityAlarmThreshold() < itemRequest.quantityReorderThreshold()) {
            throw new AlarmThresholdException();
        }
    }

    private ItemResponse buildItemResponse(final Item item) {
        final Optional<StockRecord> recentStockRecord = stockRecordMapper.findByItemId(item.getId())
                .stream().max(Comparator.comparing(StockRecord::getEffectiveDate));

        final Integer recentStockRecordQuantity = recentStockRecord.map(StockRecord::getQuantity).orElse(null);
        return buildItemResponseGivenQuantity(item, recentStockRecordQuantity);
    }

    private ItemResponse buildItemResponseGivenQuantity(final Item item, final Integer quantity) {
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

    private Item buildItemFromItemRequest(final ItemRequest itemRequest,
                                          final String itemPicturesRootUrl, final Integer vendorId) {
        Item.ItemBuilder itemBuilder = Item.builder()
                .name(itemRequest.name())
                .detail(itemRequest.detail())
                .alarmThreshold(itemRequest.quantityAlarmThreshold())
                .quantityThreshold(itemRequest.quantityReorderThreshold())
                .vendorId(vendorId).effectiveDate(Date.from(Instant.now()));

        if (itemPicturesRootUrl != null) {
            itemBuilder.pics(itemPicturesRootUrl);
        } else {
            logger.debug("Skipping to build picture url because the picture url is found to be null. " +
                    "Please try again by uploading new media");
        }

        return itemBuilder.build();
    }

    private String insertPictureAndGetUrl(final String itemPictureBase64, final String itemPictureName) {
        if (itemPictureBase64 != null) {

            return fileUploaderServiceCoordinator.uploadPicture(itemPictureBase64, itemPictureName);
        }
        return null;
    }

    private ReorderTrackerResponse handleSingleItemReorder(final Item item) {
        final Vendor vendor = vendorMapper.selectByPrimaryKey(item.getVendorId());

        try {
            if (vendor.getEmail() != null) {
                emailService.sendEmail(vendor.getEmail(), String.format("Item: %s, reorder", item.getName()),
                        NotificationServiceHelper.createItemReorderEmailBody(vendor.getName(), item.getName(), item.getReorderQuantity()));
            }

            if (vendor.getPhone() != null) {
                smsService.sendSMS(vendor.getPhone(),
                        NotificationServiceHelper.createItemReorderSMSBody(item.getName(), item.getReorderQuantity()));
            }
            //TODO: update the status to pass the string instead of integer
            reorderTrackerMapper.insert(new ReorderTracker(item.getId(), Integer.parseInt(ReorderStatus.REORDERED.name()) , Date.from(Instant.now()), vendor.getId(), ""));
            return new ReorderTrackerResponse(item.getId(), item.getVendorId(), ReorderStatus.REORDERED, "");
        } catch (Exception e) {
            //TODO: update the status to pass the string instead of integer
            reorderTrackerMapper.insert(new ReorderTracker(item.getId(), Integer.parseInt(ReorderStatus.FAILED.name()) , Date.from(Instant.now()), vendor.getId(), e.getMessage()));
            return new
                    ReorderTrackerResponse(item.getId(), item.getVendorId(), ReorderStatus.FAILED, e.getMessage());
        }
    }

    private String createItemPictureName(final String itemName,final Integer vendorId) {
        return  String.format("%s-%s-%s.%s", itemName, vendorId, System.currentTimeMillis(), "jpg");
    }
}
