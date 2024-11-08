package com.inventory_dashboard.u_backend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {
    private Integer id;

    private String name;

    private String detail;

    private String pics;

    private Integer alarmThreshold;

    private Integer quantityThreshold;

    private Integer vendorId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date effectiveDate;


}