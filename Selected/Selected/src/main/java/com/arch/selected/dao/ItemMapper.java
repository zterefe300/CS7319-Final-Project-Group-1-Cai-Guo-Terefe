package com.arch.selected.dao;

import com.arch.selected.entity.Item;
import java.util.List;

public interface ItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Item record);

    Item selectByPrimaryKey(Integer id);

    List<Item> selectAll();

    int updateByPrimaryKey(Item record);
}