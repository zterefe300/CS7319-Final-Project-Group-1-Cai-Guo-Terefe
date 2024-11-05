package com.selected.inventory_dashboard.service.interfaces;

import com.selected.inventory_dashboard.dtovo.req.ItemRequest;
import com.selected.inventory_dashboard.dtovo.res.ItemResponse;

import java.util.List;

public interface ItemService {
    List<ItemResponse> getAllItems();
    List<ItemResponse> getAllItemsWithLimit(Integer limit);
    ItemResponse insertNewItem(ItemRequest itemRequest);
    ItemResponse updateItem(Integer itemId, ItemRequest itemRequest);
    boolean deleteItem(Integer itemId);
}
