package com.selected.inventory_dashboard.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class Vendor {
    private Integer id;

    private String name;

    private String email;

    private String phone;

    private Date createTime;

    private Date updateTime;

}