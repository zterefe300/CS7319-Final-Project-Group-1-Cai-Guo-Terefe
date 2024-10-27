package com.arch.selected.service.impl;

import com.arch.selected.dao.ItemMapper;
import com.arch.selected.entity.Item;
import com.arch.selected.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: Haiyan Cai
 * @create: 2024-10-25 11:24
 * @description:
 **/
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;
    @Override
    public Item findById(Integer id) {
        return itemMapper.selectByPrimaryKey(id);
    }
}
