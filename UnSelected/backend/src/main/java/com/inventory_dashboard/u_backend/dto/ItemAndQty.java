package com.inventory_dashboard.u_backend.dto;

import com.inventory_dashboard.u_backend.entity.Item;
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
