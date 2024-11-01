package com.selected.inventory_dashboard.persistence.entity;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Item {
    private Integer id;

    private String name;

    private String detail;

    private String pics;

    private Integer qty;

    private Integer qtySold;

    private Integer threshold;

    private Integer vendorId;

    private Integer sort;

    private Date createTime;

    private Date updateTime;

}