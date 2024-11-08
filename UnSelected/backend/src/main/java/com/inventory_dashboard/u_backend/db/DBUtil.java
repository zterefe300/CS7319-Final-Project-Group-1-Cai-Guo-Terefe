package com.inventory_dashboard.u_backend.db;

import com.inventory_dashboard.u_backend.entity.Item;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Haiyan Cai
 * @create: 2024-11-07 22:41
 * @description:
 **/
@Component
@ConfigurationProperties(prefix = "spring.datasource")
public class DBUtil {
    private static final String url= "jdbc:mysql://localhost:3306/inventory";
    private static final String password = "12345678";



    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(DatasourceConfig.getDriver());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return DriverManager.getConnection(
                DatasourceConfig.getUrl(),
                DatasourceConfig.getUsername(), DatasourceConfig.getPassword());

    }

    public static List<Item> getAllItems() throws SQLException {
        String sql = "SELECT * FROM item";
        List<Item> items = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Item item = new Item();
                item.setId(rs.getInt("id"));
                item.setName(rs.getString("name"));
                item.setDetail(rs.getString("detail"));
                item.setPics(rs.getString("pics"));
                item.setAlarmThreshold(rs.getInt("alarm_threshold"));
                item.setQuantityThreshold(rs.getInt("quantity_threshold"));
                item.setVendorId(rs.getInt("vendor_id"));
                item.setEffectiveDate(rs.getTimestamp("effective_date"));

                items.add(item);
            }
        }
        return items;
    }

    public static void main(String[] args) throws SQLException {
        getConnection();
        System.out.println(getAllItems());
    }
}
