package com.selected.inventory_dashboard.persistence.entity;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockRecord {
    private Integer id;

    private Integer itemId;

    private Integer quantity;

    private Integer optType;

    private String operator;

    private Date createTime;

    private Date updateTime;
}