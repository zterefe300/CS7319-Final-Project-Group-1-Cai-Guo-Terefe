package com.selected.inventory_dashboard.dtovo.res;

import java.util.List;

public record ReorderResponseWrapper(List<ItemReorderResponse> successItemOrderResponse,
                                     List<ItemReorderResponse> failedItemOrderResponse) {
}
