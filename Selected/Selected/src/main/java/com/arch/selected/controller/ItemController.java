package com.arch.selected.controller;

import com.arch.selected.entity.Item;
import com.arch.selected.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Haiyan Cai
 * @create: 2024-10-25 11:26
 * @description:
 **/
@RestController
public class ItemController {
    @Autowired
    private ItemService itemService;
    @GetMapping("/hello")
    public Item findById(){
        return itemService.findById(1);

    }
}
