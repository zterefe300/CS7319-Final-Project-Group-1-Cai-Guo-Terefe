package com.unselected.inventory_dashboard;

import com.unselected.inventory_dashboard.db.Dao;
import com.unselected.inventory_dashboard.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

/**
 * @author: Haiyan Cai
 * @create: 2024-11-07 23:05
 * @description:
 **/

@RestController
public class TestController {


    @Autowired
    private Dao dao;
    @RequestMapping("test")
    public ResponseEntity<List<Item>> test() throws SQLException {
        List<Item> list= dao.getAllItems();
        return ResponseEntity.ok(list);
    }
}
