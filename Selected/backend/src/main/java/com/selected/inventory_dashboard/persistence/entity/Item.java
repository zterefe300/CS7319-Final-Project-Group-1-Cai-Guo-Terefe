package com.selected.inventory_dashboard.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Setter
@Getter
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