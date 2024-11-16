package com.unselected.inventory_dashboard.dto;

import com.unselected.inventory_dashboard.entity.Item;
import lombok.Data;

/**
 * @author: Haiyan Cai
 * @create: 2024-11-09 17:45
 * @description:
 **/
@Data
public class ItemAndQty extends Item {
    private Integer quantity;
}
