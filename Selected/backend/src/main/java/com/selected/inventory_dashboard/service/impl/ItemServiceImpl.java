package com.selected.inventory_dashboard.service.impl;

import com.selected.inventory_dashboard.dtovo.req.ItemRequest;
import com.selected.inventory_dashboard.dtovo.res.ItemResponse;
import com.selected.inventory_dashboard.dtovo.res.ResponseWrapper;
import com.selected.inventory_dashboard.persistence.dao.ItemMapper;
import com.selected.inventory_dashboard.persistence.dao.StockRecordMapper;
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

    public ItemServiceImpl(final ItemMapper itemMapper, final StockRecordMapper stockRecordMapper) {
        this.itemMapper = itemMapper;
        this.stockRecordMapper = stockRecordMapper;
    }

    @Override
    public ResponseWrapper<ItemResponse> insertNewItem(ItemRequest itemRequest) {
        //check if alert threshold is above re-order threshold
        if (itemRequest.quantityAlarmThreshold() < itemRequest.quantityReorderThreshold()) {
            //TODO: return error response object instead of string
            return new ResponseWrapper<>(
                    null,
                    "Alarm threshold should always be above quantity threshold"
            );
        }

        if (itemRequest.vendor() == null) {
            //TODO: return exception with detail error
            return new ResponseWrapper<>(
                    null,
                    "Vendor is not provided. Please provide a vendor"
            );
        }

        //TODO: call vendor service to create new vendor if the vendor id is null
        if(itemRequest.vendor().vendorId() == null) {
            //vendorService.createVendor(itemRequest.vendor())
        }

        //TODO: call picture files manager service to upload the pictures
        final String itemPicturesRootUrl = "";


        //TODO: once alarm and reorder threshold are available update them
        //TODO: update the insert with effective date once we have effective date
        Integer itemId = itemMapper.insert(Item.builder()
                .name(itemRequest.name())
                .detail(itemRequest.detail())
                .pics(itemPicturesRootUrl)
                .threshold(itemRequest.quantityAlarmThreshold())
                .vendorId(itemRequest.vendor().vendorId()).createTime(Date.from(Instant.now())).build());

        final Item item = itemMapper.selectByPrimaryKey(itemId);

        //Create stock record of the item with quantity one since we only add a single item.
        Integer stockRecordId = stockRecordMapper.insert(StockRecord.builder().itemId(itemId).quantity(1).createTime(Date.from(Instant.now())).build());
        final StockRecord stockRecord = stockRecordMapper.selectByPrimaryKey(stockRecordId);

        //TODO: get picture urls from the the picture files manager service
        final List<String> pictureUrls = List.of();

        return ResponseWrapper.fromSingleResponseData(new ItemResponse(
                Long.valueOf(itemId), item.getName(), item.getDetail(), pictureUrls, stockRecord.getQuantity(),
                item.getThreshold(), item.getThreshold()
        ));
    }

    @Override
    public ResponseWrapper<ItemResponse> getAllItems() {
        //TODO: update this once we have  a join query between item and stock record
        final List<ItemResponse> itemResponseList = itemMapper.selectAll().stream().map(item -> new ItemResponse(
                Long.valueOf(item.getId()),
                item.getName(),
                item.getDetail(),
                List.of(),
                stockRecordMapper.selectByPrimaryKey(item.getId()).getQuantity(),
                item.getThreshold(),
                item.getThreshold()
        )).toList();
        return ResponseWrapper.fromListOfResponseData(itemResponseList);
    }

    @Override
    public ResponseWrapper<ItemResponse> getAllItemsWithLimit(Integer limit) {
        //TODO: update this to get the items from a query that limits rows
        return ResponseWrapper.fromListOfResponseData(getAllItems().responseData().subList(0, limit));
    }

    @Override
    public ResponseWrapper<ItemResponse> updateItem(ItemRequest itemRequest) {
        //check if alert threshold is above re-order threshold
        if (itemRequest.quantityAlarmThreshold() < itemRequest.quantityReorderThreshold()) {
            //TODO: return error response object instead of string
            return new ResponseWrapper<>(
                    null,
                    "Alarm threshold should always be above quantity threshold"
            );
        }


        if (itemRequest.vendor() == null) {
            //TODO: return exception with detail error
            return new ResponseWrapper<>(
                    null,
                    "Vendor is not provided. Please provide a vendor"
            );
        }

        //TODO: Include implementation for updating quantity in stock once we have stock record get by ItemId query(Can do service call)
        //TODO: Maybe include vendor update to(Can do service call)
        final Item itemToUpdate = Item.builder()
                .name(itemRequest.name())
                .detail(itemRequest.detail())
                .threshold(itemRequest.quantityAlarmThreshold()).createTime(Date.from(Instant.now())).build();

        final int updatedItemId = itemMapper.updateByPrimaryKey(
                itemToUpdate
        );
        final Item updatedItem = itemMapper.selectByPrimaryKey(updatedItemId);

        //TODO: get list of picture urls from picture management service
        final List<String> pictureUrls = List.of();
        //TODO: get this from stock record
        final Integer currentItemQuantity = 0;

        return ResponseWrapper.fromSingleResponseData(new ItemResponse((long) updatedItemId, updatedItem.getName(), updatedItem.getDetail(),
                pictureUrls, currentItemQuantity, updatedItem.getThreshold(), updatedItem.getThreshold()));
    }

    @Override
    public ResponseWrapper<ItemResponse> deleteItem(Integer itemId) {
        itemMapper.deleteByPrimaryKey(itemId);
        return ResponseWrapper.<ItemResponse>builder().build();
    }
}