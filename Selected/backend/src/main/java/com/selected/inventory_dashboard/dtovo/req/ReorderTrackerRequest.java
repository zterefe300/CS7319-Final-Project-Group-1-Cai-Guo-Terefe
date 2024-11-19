package com.selected.inventory_dashboard.dtovo.req;

import java.util.Date;

public record ReorderTrackerRequest(Integer itemId, String status, Date date) {
}