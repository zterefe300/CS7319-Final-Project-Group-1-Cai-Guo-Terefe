package com.unselected.inventory_dashboard.dto;

public record ItemRequest(String name, String detail, Integer quantity, String pictureBase64, Integer quantityAlarmThreshold,
                          Integer quantityReorderThreshold, Integer vendorId) { }
