package com.selected.inventory_dashboard.dtovo.res;

import com.selected.inventory_dashboard.constants.ReorderStatus;

public record ItemReorderResponse(Integer itemId, Integer vendorId, ReorderStatus reorderStatus, String errorMessage) { }
