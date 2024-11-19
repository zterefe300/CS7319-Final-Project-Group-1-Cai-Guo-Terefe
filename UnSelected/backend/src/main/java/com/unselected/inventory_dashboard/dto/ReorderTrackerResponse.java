package com.unselected.inventory_dashboard.dto;

import java.util.Date;

public record ReorderTrackerResponse(Integer itemId, String itemName, Integer vendorId, String reorderStatus, String errorMessage, Date effectiveDate) { }
