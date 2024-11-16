package com.unselected.inventory_dashboard.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Haiyan Cai
 * @create: 2024-11-08 09:16
 * @description:
 **/

@Configuration
public class DatasourceConfig {
    private static String url;
    private static String username;
    private static String password;
    private static String driver;

    @Autowired
    public DatasourceConfig(@Value("${spring.datasource.url}") String url,
                            @Value("${spring.datasource.username}") String username,
                                    @Value("${spring.datasource.password}") String password,
                            @Value("${spring.datasource.driver-class-name}") String driver) {
        DatasourceConfig.url =url;
        DatasourceConfig.username =username;
        DatasourceConfig.password =password;
        DatasourceConfig.driver =driver;
    }

    public static String getUrl() {
        return url;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    public static String getDriver() {
        return driver;
    }
}