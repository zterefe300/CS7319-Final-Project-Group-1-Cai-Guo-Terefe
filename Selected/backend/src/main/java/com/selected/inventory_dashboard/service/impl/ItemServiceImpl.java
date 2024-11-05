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
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemMapper itemMapper;
    private final StockRecordMapper stockRecordMapper;
    private final VendorMapper vendorMapper;

    public ItemServiceImpl(final ItemMapper itemMapper, final StockRecordMapper stockRecordMapper, final VendorMapper vendorMapper) {
        this.itemMapper = itemMapper;
        this.stockRecordMapper = stockRecordMapper;
        this.vendorMapper = vendorMapper;
    }

    @Override
    public List<ItemResponse> getAllItems() {
        //TODO: update this once we have  a join query between item and stock record
        return itemMapper.selectAll().stream().map(item -> new ItemResponse(
                Long.valueOf(item.getId()),
                item.getName(),
                item.getDetail(),
                List.of(),
                stockRecordMapper.selectByPrimaryKey(item.getId()).getQuantity(),
                item.getAlarmThreshold(),
                item.getQuantityThreshold()
        )).toList();
    }

    @Override
    public List<ItemResponse> getAllItemsWithLimit(final Integer limit) {
        return itemMapper.selectLimit(limit).stream().map(item -> new ItemResponse(
                Long.valueOf(item.getId()),
                item.getName(),
                item.getDetail(),
                List.of(),
                stockRecordMapper.selectByPrimaryKey(item.getId()).getQuantity(),
                item.getAlarmThreshold(),
                item.getQuantityThreshold()
        )).toList();
    }

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

        //Throw no vendor data found, when a vendor id is provided but the vendor doesn't exist in thedb
        if (vendorMapper.selectByPrimaryKey(vendorRequest.vendorId()) == null) {
            throw NoVendorDataException.vendorNotFoundException();
        }

        //TODO: call picture files manager service to upload the pictures
        final String itemPicturesRootUrl = "";


        //TODO: once alarm and reorder threshold are available update them
        //TODO: update the insert with effective date once we have effective date
        Integer itemId = itemMapper.insert(Item.builder()
                .name(itemRequest.name())
                .detail(itemRequest.detail())
                .pics(itemPicturesRootUrl)
                .quantityThreshold(itemRequest.quantityAlarmThreshold())
                .vendorId(itemRequest.vendor().vendorId()).effectiveDate(Date.from(Instant.now())).build());

        final Item item = itemMapper.selectByPrimaryKey(itemId);

        //Create stock record of the item with quantity one since we only add a single item.
        Integer stockRecordId = stockRecordMapper.insert(StockRecord.builder().itemId(itemId).quantity(1).effectiveDate(Date.from(Instant.now())).build());
        final StockRecord stockRecord = stockRecordMapper.selectByPrimaryKey(stockRecordId);

        //TODO: get picture urls from the the picture files manager service
        final List<String> pictureUrls = List.of();


        return new ItemResponse(
                Long.valueOf(itemId), item.getName(), item.getDetail(), pictureUrls, stockRecord.getQuantity(),
                item.getAlarmThreshold(), item.getQuantityThreshold()
        );
    }

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

        //TODO: Include implementation for updating quantity in stock once we have stock record get by ItemId query(Can do service call)
        //TODO: Maybe include vendor update to(Can do service call)
        final Item itemToUpdate = Item.builder()
                .name(itemRequest.name())
                .detail(itemRequest.detail())
                .quantityThreshold(itemRequest.quantityAlarmThreshold()).effectiveDate(Date.from(Instant.now())).build();

        final int updatedItemId = itemMapper.updateByPrimaryKey(
                itemToUpdate
        );
        final Item updatedItem = itemMapper.selectByPrimaryKey(updatedItemId);

        //TODO: get list of picture urls from picture management service
        final List<String> pictureUrls = List.of();
        //TODO: get this from stock record
        final Integer currentItemQuantity = 0;

        return new ItemResponse((long) updatedItemId, updatedItem.getName(), updatedItem.getDetail(),
                pictureUrls, currentItemQuantity, updatedItem.getAlarmThreshold(), updatedItem.getQuantityThreshold());
    }

    @Override
    public boolean deleteItem(final Integer itemId) {
        itemMapper.deleteByPrimaryKey(itemId);
        return itemMapper.selectByPrimaryKey(itemId) == null;
    }
}
