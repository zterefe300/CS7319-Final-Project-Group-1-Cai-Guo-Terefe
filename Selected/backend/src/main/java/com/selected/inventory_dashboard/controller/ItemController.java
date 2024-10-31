package com.selected.inventory_dashboard.controller;

import com.selected.inventory_dashboard.persistence.entity.Item;
import com.selected.inventory_dashboard.service.interfaces.ItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Haiyan Cai
 * @create: 2024-10-25 11:26
 * @description:
 **/
@RestController
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/hello")
    public Item findById(){
       return null;
    }
}
