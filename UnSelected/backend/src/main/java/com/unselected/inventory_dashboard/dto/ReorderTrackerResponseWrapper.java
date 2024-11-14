package com.unselected.inventory_dashboard.dto;

import java.util.List;

public record ReorderTrackerResponseWrapper(List<ReorderTrackerResponse> successItemOrderResponse,
                                            List<ReorderTrackerResponse> failedItemOrderResponse) {
}
