package com.unselected.inventory_dashboard.dto;

import com.unselected.inventory_dashboard.constants.ReorderStatus;

public record ReorderTrackerResponse(Integer itemId, Integer vendorId, ReorderStatus reorderStatus, String errorMessage) { }
