package com.unselected.inventory_dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class InventoryUnselectedApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryUnselectedApplication.class, args);
    }

}
