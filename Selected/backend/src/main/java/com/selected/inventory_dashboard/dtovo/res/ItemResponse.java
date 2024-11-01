package com.selected.inventory_dashboard.dtovo.res;

import java.util.List;

public record ItemResponse(Long itemId, String name, String description, List<String> pictureUrls, Integer quantity,
                           Integer quantityAlarmThreshold, Integer quantityReorderThreshold) {
}