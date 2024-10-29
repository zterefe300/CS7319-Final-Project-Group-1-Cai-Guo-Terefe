package com.selected.inventory_dashboard;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.selected.inventory_dashboard.persistence.dao")
public class InventorySelectedApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventorySelectedApplication.class, args);
    }

}