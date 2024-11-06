package com.selected.inventory_dashboard.service.impl;

import com.selected.inventory_dashboard.dtovo.req.ItemRequest;
import com.selected.inventory_dashboard.dtovo.req.VendorRequest;
import com.selected.inventory_dashboard.dtovo.res.ItemResponse;
import com.selected.inventory_dashboard.exception.AlarmThresholdException;
import com.selected.inventory_dashboard.exception.NoItemDataException;
import com.selected.inventory_dashboard.exception.NoVendorDataException;
import com.selected.inventory_dashboard.persistence.dao.ItemMapper;
import com.selected.inventory_dashboard.persistence.dao.StockRecordMapper;
import com.selected.inventory_dashboard.persistence.dao.VendorMapper;
import com.selected.inventory_dashboard.persistence.entity.Item;
import com.selected.inventory_dashboard.persistence.entity.StockRecord;
import com.selected.inventory_dashboard.service.interfaces.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    private static final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);

    private final ItemMapper itemMapper;
    private final StockRecordMapper stockRecordMapper;
    private final VendorMapper vendorMapper;
    private final FileUploaderServiceCoordinator fileUploaderServiceCoordinator;

    public ItemServiceImpl(final ItemMapper itemMapper, final StockRecordMapper stockRecordMapper, final VendorMapper vendorMapper, final FileUploaderServiceCoordinator fileUploaderServiceCoordinator) {
        this.itemMapper = itemMapper;
        this.stockRecordMapper = stockRecordMapper;
        this.vendorMapper = vendorMapper;
        this.fileUploaderServiceCoordinator = fileUploaderServiceCoordinator;
    }

    @Override
    public List<ItemResponse> getAllItems() {
        //TODO: update this once we have  a join query between item and stock record
        return itemMapper.selectAll().stream().map(item -> new ItemResponse(
                item.getId(),
                item.getName(),
                item.getDetail(),
                item.getPics(),
                stockRecordMapper.selectByPrimaryKey(item.getId()).getQuantity(),
                item.getAlarmThreshold(),
                item.getQuantityThreshold()
        )).toList();
    }

    @Override
    public List<ItemResponse> getAllItemsWithLimit(final Integer limit) {
        return itemMapper.selectLimit(limit).stream().map(item -> new ItemResponse(
                item.getId(),
                item.getName(),
                item.getDetail(),
                item.getPics(),
                stockRecordMapper.selectByPrimaryKey(item.getId()).getQuantity(),
                item.getAlarmThreshold(),
                item.getQuantityThreshold()
        )).toList();
    }

    ///TODO: Breakdown service into methods. Add error handling for insert operations
    @Override
    public ItemResponse insertNewItem(final ItemRequest itemRequest) {
        //check if alert threshold is above re-order threshold
        if (itemRequest.quantityAlarmThreshold() < itemRequest.quantityReorderThreshold()) {
           throw new AlarmThresholdException();
        }

        final VendorRequest vendorRequest = itemRequest.vendor();

        //Throw default no vendor data exception, if vendor data is not provided as part if the request
        if (vendorRequest == null || vendorRequest.vendorId() == null) {
            throw new NoVendorDataException();
        }

        //Throw no vendor data found, when a vendor id is provided but the vendor doesn't exist in the db
        if (vendorMapper.selectByPrimaryKey(vendorRequest.vendorId()) == null) {
            throw NoVendorDataException.vendorNotFoundException();
        }

        final MultipartFile pictureStream = itemRequest.picturesStream();
        //TODO: Pass custom name with the item id.
        final String itemPicturesRootUrl = insertOrUpdatePictureAndGetUrl(pictureStream);

        Integer itemId = itemMapper.insert(Item.builder()
                .name(itemRequest.name())
                .detail(itemRequest.detail())
                .pics(itemPicturesRootUrl)
                .alarmThreshold(itemRequest.quantityAlarmThreshold())
                .quantityThreshold(itemRequest.quantityReorderThreshold())
                .vendorId(itemRequest.vendor().vendorId()).effectiveDate(Date.from(Instant.now())).build());

        final Item item = itemMapper.selectByPrimaryKey(itemId);

        //Create stock record of the item with quantity one since we only add a single item.
        Integer stockRecordId = stockRecordMapper.insert(StockRecord.builder().itemId(itemId)
                .quantity(1).effectiveDate(Date.from(Instant.now())).build());
        final StockRecord stockRecord = stockRecordMapper.selectByPrimaryKey(stockRecordId);

        return new ItemResponse(
                itemId, item.getName(), item.getDetail(), item.getPics(), stockRecord.getQuantity(),
                item.getAlarmThreshold(), item.getQuantityThreshold()
        );
    }

    ///TODO: Breakdown service into methods. Add error handling for update operations
    @Override
    public ItemResponse updateItem(final Integer itemId, final ItemRequest itemRequest) {
        //check if alert threshold is above re-order threshold
        if (itemRequest.quantityAlarmThreshold() < itemRequest.quantityReorderThreshold()) {
            throw new AlarmThresholdException();
        }

        if (itemMapper.selectByPrimaryKey(itemId) == null) {
            throw new NoItemDataException();
        }

        final VendorRequest vendorRequest = itemRequest.vendor();

        if (vendorRequest != null && vendorMapper.selectByPrimaryKey(vendorRequest.vendorId()) == null) {
            throw NoVendorDataException.vendorNotFoundException();
        }

        final Item itemToUpdate = Item.builder()
                .name(itemRequest.name())
                .detail(itemRequest.detail())
                .alarmThreshold(itemRequest.quantityAlarmThreshold())
                .quantityThreshold(itemRequest.quantityReorderThreshold())
                .effectiveDate(Date.from(Instant.now())).build();

        //TODO: append the item id and provide custom filename
        //TODO: delete the existing picture url
        final String updatedPictureFileUrl = insertOrUpdatePictureAndGetUrl(itemRequest.picturesStream());
        //Avoid updating current item picture url if the new updated picture url is null
        if (updatedPictureFileUrl != null) {
            itemToUpdate.setPics(updatedPictureFileUrl);
        } else {
            logger.debug("Skipping to update picture url because the new updated url is found to be null. " +
                    "Please try again by uploading new media");
        }

        //Update item
        itemMapper.updateByPrimaryKey(
                itemToUpdate
        );
        //Update stock record
         stockRecordMapper.updateByPrimaryKey(StockRecord.builder()
                .itemId(itemId).quantity(itemRequest.quantity())
                .effectiveDate(Date.from(Instant.now())).build());

        final Item updatedItem = itemMapper.selectByPrimaryKey(itemId);
        return new ItemResponse(itemId, updatedItem.getName(), updatedItem.getDetail(),
                updatedItem.getPics(), itemRequest.quantity(), updatedItem.getAlarmThreshold(), updatedItem.getQuantityThreshold());
    }

    @Override
    public boolean deleteItem(final Integer itemId) {
        itemMapper.deleteByPrimaryKey(itemId);
        return itemMapper.selectByPrimaryKey(itemId) == null;
    }

    private String insertOrUpdatePictureAndGetUrl(final MultipartFile pictureFile) {
        if (pictureFile != null) {
            return fileUploaderServiceCoordinator.uploadPicture(pictureFile, "");
        }
        return null;
    }
}
