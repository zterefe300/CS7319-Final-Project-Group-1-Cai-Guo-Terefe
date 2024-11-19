package com.selected.inventory_dashboard.dtovo.req;

import lombok.Data;

/**
 * @author: Haiyan Cai
 * @create: 2024-11-18 12:37
 * @description:
 **/

@Data
public class UpdateStockReq {
    private int itemId;
    private int quantity;
    private String secretKey;
}
