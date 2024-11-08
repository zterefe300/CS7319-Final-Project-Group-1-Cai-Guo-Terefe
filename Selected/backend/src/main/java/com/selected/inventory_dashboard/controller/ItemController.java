package com.selected.inventory_dashboard.controller;

import com.selected.inventory_dashboard.dtovo.req.ItemRequest;
import com.selected.inventory_dashboard.dtovo.res.ItemResponse;
import com.selected.inventory_dashboard.service.interfaces.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: Haiyan Cai
 * @create: 2024-10-25 11:26
 * @description:
 **/
@RestController
@RequestMapping("/api/items")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<List<ItemResponse>> getAllItems() {
        return ResponseEntity.ok(itemService.getAllItems());
    }

    @GetMapping("/{limit}")
    public ResponseEntity<List<ItemResponse>> getItemsWithLimit(@PathVariable Integer limit) {
        return ResponseEntity.ok(itemService.getAllItemsWithLimit(limit));
    }

    @PostMapping
    public ResponseEntity<ItemResponse> createItem(@RequestBody ItemRequest itemRequest) {
        return ResponseEntity.ok(itemService.insertNewItem(itemRequest));
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<ItemResponse> updateItem(@PathVariable Integer itemId, @RequestBody ItemRequest itemRequest) {
        return ResponseEntity.ok(itemService.updateItem(itemId, itemRequest));
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Boolean> deleteItem(@PathVariable Integer itemId) {
        return ResponseEntity.ok(itemService.deleteItem(itemId));
    }
}
