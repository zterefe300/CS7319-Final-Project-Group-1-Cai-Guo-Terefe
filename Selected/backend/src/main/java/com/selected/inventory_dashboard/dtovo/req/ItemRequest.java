package com.selected.inventory_dashboard.dtovo.req;

import org.springframework.web.multipart.MultipartFile;

public record ItemRequest(String name, String detail, Integer quantity, MultipartFile picturesStream, Integer quantityAlarmThreshold,
                          Integer quantityReorderThreshold, VendorRequest vendor) { }
