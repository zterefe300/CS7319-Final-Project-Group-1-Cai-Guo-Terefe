package com.selected.inventory_dashboard.dtovo.res;

import java.util.Date;

public record ReorderTrackerResponse(Integer itemId, Integer vendorId,
                                     String reorderStatus, String errorMessage, Date effectiveDate) { }
