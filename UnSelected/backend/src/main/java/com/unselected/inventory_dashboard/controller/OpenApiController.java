package com.unselected.inventory_dashboard.controller;


import com.unselected.inventory_dashboard.db.Dao;
import com.unselected.inventory_dashboard.dto.UpdateStockReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Haiyan Cai
 * @create: 2024-11-17 23:14
 * @description:
 **/

@RestController
@RequestMapping("/openApi")
public class OpenApiController {
    @Autowired
    private Dao dao;

    @PostMapping("/checkout")
    ResponseEntity<Boolean> checkout(@RequestBody UpdateStockReq req){
        if(!"@jFVdYvye7UfYqp)X4t$Lg6uW3bZVGX64hX~wkN2%db+mxM0!l".equals(req.getSecretKey())){
            return ResponseEntity.badRequest().build();
        }
        Boolean res = dao.updateStock(req.getItemId(),req.getQuantity());
        return ResponseEntity.ok(res);
    }

}
