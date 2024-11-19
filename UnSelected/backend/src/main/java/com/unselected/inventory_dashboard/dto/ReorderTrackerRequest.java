package com.unselected.inventory_dashboard.dto;

import java.util.Date;

public record ReorderTrackerRequest(Integer itemId, String status, Date date) {
}