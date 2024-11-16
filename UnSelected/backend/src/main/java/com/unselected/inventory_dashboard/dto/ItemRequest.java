package com.unselected.inventory_dashboard.dto;

import org.springframework.web.multipart.MultipartFile;

public record ItemRequest(String name, String detail, Integer quantity, MultipartFile pictureStream, Integer quantityAlarmThreshold,
                          Integer quantityReorderThreshold, Integer vendorId) { }
