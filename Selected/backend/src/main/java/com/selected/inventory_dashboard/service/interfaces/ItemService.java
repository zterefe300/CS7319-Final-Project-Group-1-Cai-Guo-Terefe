package com.selected.inventory_dashboard.service.interfaces;

import com.selected.inventory_dashboard.persistence.entity.Item;

public interface ItemService {
    Item findById(Integer id);
}
