package com.unselected.inventory_dashboard.dto;

public record ItemResponse(Integer itemId, String name, String description, String pictureUrl, Integer quantity,
                           Integer quantityAlarmThreshold, Integer quantityReorderThreshold, Integer vendorId) {
}