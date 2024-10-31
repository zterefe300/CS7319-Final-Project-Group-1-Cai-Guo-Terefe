package com.selected.inventory_dashboard.dtovo.req;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record ItemRequest(String name, String detail, Integer quantity, List<MultipartFile> picturesStream, Integer quantityAlarmThreshold,
                          Integer quantityReorderThreshold, VendorRequest vendor) { }
