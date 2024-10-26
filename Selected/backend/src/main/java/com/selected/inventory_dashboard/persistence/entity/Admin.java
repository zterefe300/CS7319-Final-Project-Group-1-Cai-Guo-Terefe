package com.selected.inventory_dashboard.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class Admin {
    private Integer id;

    private String username;

    private String password;

    private Date createTime;

    private Date updateTime;

}