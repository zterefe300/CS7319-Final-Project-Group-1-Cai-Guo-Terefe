package com.selected.inventory_dashboard.service.interfaces;

import com.selected.inventory_dashboard.dtovo.req.ItemRequest;
import com.selected.inventory_dashboard.dtovo.req.ReorderTrackerRequest;
import com.selected.inventory_dashboard.dtovo.res.ItemResponse;
import com.selected.inventory_dashboard.dtovo.res.ReorderTrackerResponse;
import com.selected.inventory_dashboard.dtovo.res.ReorderTrackerResponseWrapper;

import java.util.List;

public interface ItemService {
    List<ItemResponse> getAllItems();
    List<ItemResponse> getAllItemsWithLimit(Integer limit);
    List<ReorderTrackerResponse> getReorderTrackerData();
    ItemResponse insertNewItem(ItemRequest itemRequest);
    ItemResponse updateItem(Integer itemId, ItemRequest itemRequest);
    ReorderTrackerResponse fulfillItemReorder(ReorderTrackerRequest reorderTrackerRequest);
    boolean deleteItem(Integer itemId);
    ReorderTrackerResponseWrapper reorderItemsLowStockItems();
    void sendAlarmForItemsBelowAlarmThreshold();

    boolean updateStock(int itemId, int quantity);
}