package com.selected.inventory_dashboard.persistence.dao;

import com.selected.inventory_dashboard.persistence.entity.Item;

import java.util.List;

public interface ItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Item record);

    Item selectByPrimaryKey(Integer id);

    List<Item> selectAll();

    int updateByPrimaryKey(Item record);
}