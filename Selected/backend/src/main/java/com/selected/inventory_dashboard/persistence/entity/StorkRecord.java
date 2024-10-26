package com.selected.inventory_dashboard.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class StorkRecord {
    private Integer id;

    private Integer itemId;

    private Integer quantity;

    private Boolean optType;

    private String operator;

    private Date createTime;

    private Date updateTime;

}