package com.selected.inventory_dashboard.dtovo.res;

import java.util.List;

public record ReorderTrackerResponseWrapper(List<ReorderTrackerResponse> successItemOrderResponse,
                                            List<ReorderTrackerResponse> failedItemOrderResponse) {
}
