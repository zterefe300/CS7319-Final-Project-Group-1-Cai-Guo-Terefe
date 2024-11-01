package com.selected.inventory_dashboard.controller;

import com.selected.inventory_dashboard.dtovo.req.ItemRequest;
import com.selected.inventory_dashboard.dtovo.res.ItemResponse;
import com.selected.inventory_dashboard.dtovo.res.ResponseWrapper;
import com.selected.inventory_dashboard.service.interfaces.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ResponseWrapper<ItemResponse>> getAllItems() {
        return ResponseEntity.ok(itemService.getAllItems());
    }

    @GetMapping("/{limit}")
    public ResponseEntity<ResponseWrapper<ItemResponse>> getItemsWithLimit(@PathVariable Integer limit) {
        return ResponseEntity.ok(itemService.getAllItemsWithLimit(limit));
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<ItemResponse>> createItem(@RequestBody ItemRequest itemRequest) {
        final ResponseWrapper<ItemResponse> itemResponseResponseWrapper = itemService.insertNewItem(itemRequest);
        final String errorMessage = itemResponseResponseWrapper.errorMessage();

        return (errorMessage == null || errorMessage.isEmpty()) ?
                ResponseEntity.ok(itemResponseResponseWrapper) :
                ResponseEntity.badRequest().body(itemResponseResponseWrapper);
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<ResponseWrapper<ItemResponse>> updateItem(@PathVariable Integer itemId, @RequestBody ItemRequest itemRequest) {
        final ResponseWrapper<ItemResponse> itemResponseResponseWrapper = itemService.updateItem(itemId, itemRequest);
        final String errorMessage = itemResponseResponseWrapper.errorMessage();

        return (errorMessage == null || errorMessage.isEmpty()) ?
                ResponseEntity.ok(itemResponseResponseWrapper) :
                ResponseEntity.badRequest().body(itemResponseResponseWrapper);

    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<ResponseWrapper<ItemResponse>> deleteItem(@PathVariable Integer itemId) {
        return ResponseEntity.ok(itemService.deleteItem(itemId));
    }
}
