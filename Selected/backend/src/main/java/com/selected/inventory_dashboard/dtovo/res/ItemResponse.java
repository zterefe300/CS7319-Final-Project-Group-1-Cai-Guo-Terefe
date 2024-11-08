package com.selected.inventory_dashboard.dtovo.res;

public record ItemResponse(Integer itemId, String name, String description, String pictureUrl, Integer quantity,
                           Integer quantityAlarmThreshold, Integer quantityReorderThreshold) {
}