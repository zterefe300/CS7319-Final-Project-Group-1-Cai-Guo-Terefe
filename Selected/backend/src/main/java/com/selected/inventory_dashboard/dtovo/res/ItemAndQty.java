package com.selected.inventory_dashboard.dtovo.res;

import com.selected.inventory_dashboard.persistence.entity.Item;
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
