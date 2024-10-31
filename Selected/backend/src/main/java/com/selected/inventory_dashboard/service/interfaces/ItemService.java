package com.selected.inventory_dashboard.service.interfaces;

import com.selected.inventory_dashboard.dtovo.req.ItemRequest;
import com.selected.inventory_dashboard.dtovo.res.ItemResponse;
import com.selected.inventory_dashboard.dtovo.res.ResponseWrapper;

public interface ItemService {
    ResponseWrapper<ItemResponse> insertNewItem(ItemRequest itemRequest);
    ResponseWrapper<ItemResponse> getAllItems();
    ResponseWrapper<ItemResponse> getAllItemsWithLimit(Integer limit);
    ResponseWrapper<ItemResponse> updateItem(ItemRequest itemRequest);
    ResponseWrapper<ItemResponse> deleteItem(Integer itemId);
}
