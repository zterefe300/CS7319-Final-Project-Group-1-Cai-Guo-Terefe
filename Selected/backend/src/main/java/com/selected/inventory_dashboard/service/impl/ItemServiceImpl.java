package com.selected.inventory_dashboard.service.impl;

import com.selected.inventory_dashboard.persistence.dao.ItemMapper;
import com.selected.inventory_dashboard.persistence.entity.Item;
import com.selected.inventory_dashboard.service.interfaces.ItemService;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemMapper itemMapper;

    public  ItemServiceImpl(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    @Override
    public Item findById(Integer id) {
        return itemMapper.selectByPrimaryKey(id);
    }
}
