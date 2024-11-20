package com.selected.inventory_dashboard.dtovo.req;

public record ItemRequest(String name, String detail, Integer quantity, String pictureBase64, Integer quantityAlarmThreshold,
                          Integer quantityReorderThreshold, Integer vendorId) { }
