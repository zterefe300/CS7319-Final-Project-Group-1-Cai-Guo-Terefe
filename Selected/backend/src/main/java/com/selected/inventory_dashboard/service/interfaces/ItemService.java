package com.selected.inventory_dashboard.service.interfaces;

import com.selected.inventory_dashboard.dtovo.req.ItemRequest;
import com.selected.inventory_dashboard.dtovo.res.ItemResponse;
import com.selected.inventory_dashboard.dtovo.res.ResponseWrapper;

public interface ItemService {
    ResponseWrapper<ItemResponse> getAllItems();
    ResponseWrapper<ItemResponse> getAllItemsWithLimit(Integer limit);
    ResponseWrapper<ItemResponse> insertNewItem(ItemRequest itemRequest);
    ResponseWrapper<ItemResponse> updateItem(Integer itemId, ItemRequest itemRequest);
    ResponseWrapper<ItemResponse> deleteItem(Integer itemId);
}
