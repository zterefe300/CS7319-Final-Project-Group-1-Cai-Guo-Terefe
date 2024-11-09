package com.inventory_dashboard.u_backend.db;

import com.inventory_dashboard.u_backend.entity.Item;
import com.inventory_dashboard.u_backend.entity.StockRecord;
import com.inventory_dashboard.u_backend.entity.User;
import com.inventory_dashboard.u_backend.entity.Vendor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Haiyan Cai
 * @create: 2024-11-07 22:33
 * @description:
 **/

@Repository
public class Dao {


    //----------------------------------Item------------------------------------------------------------------------------

    public void createItem(Item item) throws SQLException {
        String sql = "INSERT INTO item (name, detail, pics, alarm_threshold, quantity_threshold, vendor_id, effective_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.getName());
            stmt.setString(2, item.getDetail());
            stmt.setString(3, item.getPics());
            stmt.setInt(4, item.getAlarmThreshold());
            stmt.setInt(5, item.getQuantityThreshold());
            stmt.setInt(6, item.getVendorId());
            stmt.setTimestamp(7, new Timestamp(item.getEffectiveDate().getTime()));
            stmt.executeUpdate();
        }
    }

    public Item getItem(int id) throws SQLException {
        String sql = "SELECT * FROM item WHERE id = ?";
        Item item = null;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    item = new Item();
                    item.setId(rs.getInt("id"));
                    item.setName(rs.getString("name"));
                    item.setDetail(rs.getString("detail"));
                    item.setPics(rs.getString("pics"));
                    item.setAlarmThreshold(rs.getInt("alarm_threshold"));
                    item.setQuantityThreshold(rs.getInt("quantity_threshold"));
                    item.setVendorId(rs.getInt("vendor_id"));
                    item.setEffectiveDate(rs.getTimestamp("effective_date"));
                }
            }
        }
        return item;
    }

    public void updateItem(Item item) throws SQLException {
        String sql = "UPDATE item SET name = ?, detail = ?, pics = ?, alarm_threshold = ?, quantity_threshold = ?, vendor_id = ?, effective_date = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.getName());
            stmt.setString(2, item.getDetail());
            stmt.setString(3, item.getPics());
            stmt.setInt(4, item.getAlarmThreshold());
            stmt.setInt(5, item.getQuantityThreshold());
            stmt.setInt(6, item.getVendorId());
            stmt.setTimestamp(7, new Timestamp(item.getEffectiveDate().getTime()));
            stmt.setInt(8, item.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteItem(int id) throws SQLException {
        String sql = "DELETE FROM item WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Item> getAllItems() throws SQLException {
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


    //----------------------------------StockRecord------------------------------------------------------------------------------


    public void createStockRecord(StockRecord stockRecord) throws SQLException {
        String sql = "INSERT INTO stock_record (item_id, quantity, operator, effective_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, stockRecord.getItemId());
            stmt.setInt(2, stockRecord.getQuantity());
            stmt.setString(3, stockRecord.getOperator());
            stmt.setTimestamp(4, new Timestamp(stockRecord.getEffectiveDate().getTime()));
            stmt.executeUpdate();
        }
    }

    public StockRecord getStockRecord(int id) throws SQLException {
        String sql = "SELECT * FROM stock_record WHERE id = ?";
        StockRecord stockRecord = null;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    stockRecord = new StockRecord();
                    stockRecord.setId(rs.getInt("id"));
                    stockRecord.setItemId(rs.getInt("item_id"));
                    stockRecord.setQuantity(rs.getInt("quantity"));
                    stockRecord.setOperator(rs.getString("operator"));
                    stockRecord.setEffectiveDate(rs.getTimestamp("effective_date"));
                }
            }
        }
        return stockRecord;
    }

    public void updateStockRecord(StockRecord stockRecord) throws SQLException {
        String sql = "UPDATE stock_record SET item_id = ?, quantity = ?, operator = ?, effective_date = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, stockRecord.getItemId());
            stmt.setInt(2, stockRecord.getQuantity());
            stmt.setString(3, stockRecord.getOperator());
            stmt.setTimestamp(4, new Timestamp(stockRecord.getEffectiveDate().getTime()));
            stmt.setInt(5, stockRecord.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteStockRecord(int id) throws SQLException {
        String sql = "DELETE FROM stock_record WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<StockRecord> getAllStockRecords() throws SQLException {
        String sql = "SELECT * FROM stock_record";
        List<StockRecord> stockRecords = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                StockRecord stockRecord = new StockRecord();
                stockRecord.setId(rs.getInt("id"));
                stockRecord.setItemId(rs.getInt("item_id"));
                stockRecord.setQuantity(rs.getInt("quantity"));
                stockRecord.setOperator(rs.getString("operator"));
                stockRecord.setEffectiveDate(rs.getTimestamp("effective_date"));

                stockRecords.add(stockRecord);
            }
        }
        return stockRecords;
    }



    //----------------------------------User------------------------------------------------------------------------------


    public void createUser(User user) throws SQLException {
        String sql = "INSERT INTO user (user_name, password, first_name, last_name, role_type, create_time, update_time) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getFirstName());
            stmt.setString(4, user.getLastName());
            stmt.setInt(5, user.getRoleType());
            stmt.setTimestamp(6, new Timestamp(user.getCreateTime().getTime()));
            stmt.setTimestamp(7, new Timestamp(user.getUpdateTime().getTime()));
            stmt.executeUpdate();
        }
    }

    public User getUser(int id) throws SQLException {
        String sql = "SELECT * FROM user WHERE id = ?";
        User user = null;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUserName(rs.getString("user_name"));
                    user.setPassword(rs.getString("password"));
                    user.setFirstName(rs.getString("first_name"));
                    user.setLastName(rs.getString("last_name"));
                    user.setRoleType(rs.getInt("role_type"));
                    user.setCreateTime(rs.getTimestamp("create_time"));
                    user.setUpdateTime(rs.getTimestamp("update_time"));
                }
            }
        }
        return user;
    }

    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE user SET user_name = ?, password = ?, first_name = ?, last_name = ?, role_type = ?, update_time = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getFirstName());
            stmt.setString(4, user.getLastName());
            stmt.setInt(5, user.getRoleType());
            stmt.setTimestamp(6, new Timestamp(user.getUpdateTime().getTime()));
            stmt.setInt(7, user.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteUser(int id) throws SQLException {
        String sql = "DELETE FROM user WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<User> getAllUsers() throws SQLException {
        String sql = "SELECT * FROM user";
        List<User> users = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("user_name"));
                user.setPassword(rs.getString("password"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setRoleType(rs.getInt("role_type"));
                user.setCreateTime(rs.getTimestamp("create_time"));
                user.setUpdateTime(rs.getTimestamp("update_time"));

                users.add(user);
            }
        }
        return users;
    }

    public User selectByUserName(String userName) throws SQLException {
        String sql = "SELECT * FROM user WHERE user_name = ?";
        User user = null;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, userName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUserName(rs.getString("user_name"));
                    user.setPassword(rs.getString("password"));
                    user.setFirstName(rs.getString("first_name"));
                    user.setLastName(rs.getString("last_name"));
                    user.setRoleType(rs.getInt("role_type"));
                    user.setCreateTime(rs.getTimestamp("create_time"));
                    user.setUpdateTime(rs.getTimestamp("update_time"));
                }
            }
        }
        return user;
    }



    //----------------------------------vendor------------------------------------------------------------------------------

    public void createVendor(Vendor vendor) throws SQLException {
        String sql = "INSERT INTO vendor (name, email, phone) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, vendor.getName());
            stmt.setString(2, vendor.getEmail());
            stmt.setString(3, vendor.getPhone());
            stmt.executeUpdate();
        }
    }

    public Vendor getVendor(int id) throws SQLException {
        String sql = "SELECT * FROM vendor WHERE id = ?";
        Vendor vendor = null;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    vendor = new Vendor();
                    vendor.setId(rs.getInt("id"));
                    vendor.setName(rs.getString("name"));
                    vendor.setEmail(rs.getString("email"));
                    vendor.setPhone(rs.getString("phone"));
                    vendor.setCreateTime(rs.getTimestamp("create_time"));
                    vendor.setUpdateTime(rs.getTimestamp("update_time"));
                }
            }
        }
        return vendor;
    }

    public void updateVendor(Vendor vendor) throws SQLException {
        String sql = "UPDATE vendor SET name = ?, email = ?, phone = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, vendor.getName());
            stmt.setString(2, vendor.getEmail());
            stmt.setString(3, vendor.getPhone());
            stmt.setInt(4, vendor.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteVendor(int id) throws SQLException {
        String sql = "DELETE FROM vendor WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Vendor> getAllVendors() throws SQLException {
        String sql = "SELECT * FROM vendor";
        List<Vendor> vendors = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Vendor vendor = new Vendor();
                vendor.setId(rs.getInt("id"));
                vendor.setName(rs.getString("name"));
                vendor.setEmail(rs.getString("email"));
                vendor.setPhone(rs.getString("phone"));
                vendor.setCreateTime(rs.getTimestamp("create_time"));
                vendor.setUpdateTime(rs.getTimestamp("update_time"));

                vendors.add(vendor);
            }
        }
        return vendors;
    }
}
