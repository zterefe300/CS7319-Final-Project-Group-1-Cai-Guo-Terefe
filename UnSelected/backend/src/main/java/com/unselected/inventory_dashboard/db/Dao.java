package com.unselected.inventory_dashboard.db;

import com.unselected.inventory_dashboard.dto.ItemAndQty;
import com.unselected.inventory_dashboard.entity.*;
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

    public Integer createItem(Item item){
        String sql = "INSERT INTO item (name, detail, pics, alarm_threshold, quantity_threshold, vendor_id, effective_date,reorder_quantity) VALUES (?, ?, ?, ?, ?, ?, ?,?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

            stmt.setString(1, item.getName());
            stmt.setString(2, item.getDetail());
            stmt.setString(3, item.getPics());
            stmt.setInt(4, item.getAlarmThreshold());
            stmt.setInt(5, item.getQuantityThreshold());
            stmt.setInt(6, item.getVendorId());
            stmt.setTimestamp(7, new Timestamp(item.getEffectiveDate().getTime()));
            stmt.setInt(8,item.getReorderQuantity());
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Failed to insert item, no rows affected");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Failed to insert item, no ID obtained");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Item getItem(int id){
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
                    item.setReorderQuantity(rs.getInt("reorder_quantity"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return item;
    }

    public void updateItem(Item item){
        String sql = "UPDATE item SET name = ?, detail = ?, pics = ?, alarm_threshold = ?, quantity_threshold = ?, vendor_id = ?, effective_date = ?, reorder_quantity=? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.getName());
            stmt.setString(2, item.getDetail());
            stmt.setString(3, item.getPics());
            stmt.setInt(4, item.getAlarmThreshold());
            stmt.setInt(5, item.getQuantityThreshold());
            stmt.setInt(6, item.getVendorId());
            stmt.setTimestamp(7, new Timestamp(item.getEffectiveDate().getTime()));
            stmt.setInt(8,item.getReorderQuantity());
            stmt.setInt(9, item.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteItem(int id){
        String sql = "DELETE FROM item WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Item> getAllItems(){
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
                item.setReorderQuantity(rs.getInt("reorder_quantity"));

                items.add(item);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return items;
    }

    public List<Item> selectLimit(int limit) {
        String sql = "SELECT * FROM item limit ?";
        List<Item> items = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, limit);
             ResultSet rs = stmt.executeQuery();

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
                item.setReorderQuantity(rs.getInt("reorder_quantity"));

                items.add(item);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return items;
    }

    public List<ItemAndQty> findAllBelowQtyThreshold(){
        String sql = "select item.*,stock_record.quantity from item left join stock_record on item.id=stock_record.item_id\n" +
                "    where stock_record.quantity < item.quantity_threshold ";
        List<ItemAndQty> items = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ItemAndQty item = new ItemAndQty();
                item.setId(rs.getInt("id"));
                item.setName(rs.getString("name"));
                item.setDetail(rs.getString("detail"));
                item.setPics(rs.getString("pics"));
                item.setAlarmThreshold(rs.getInt("alarm_threshold"));
                item.setQuantityThreshold(rs.getInt("quantity_threshold"));
                item.setVendorId(rs.getInt("vendor_id"));
                item.setEffectiveDate(rs.getTimestamp("effective_date"));
                item.setReorderQuantity(rs.getInt("reorder_quantity"));
                item.setQuantity(rs.getInt("quantity"));

                items.add(item);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return items;
    }


    //----------------------------------StockRecord------------------------------------------------------------------------------


    public void createStockRecord(StockRecord stockRecord){
        String sql = "INSERT INTO stock_record (item_id, quantity, operator, effective_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, stockRecord.getItemId());
            stmt.setInt(2, stockRecord.getQuantity());
            stmt.setString(3, stockRecord.getOperator());
            stmt.setTimestamp(4, new Timestamp(stockRecord.getEffectiveDate().getTime()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public StockRecord getStockRecord(int id) {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return stockRecord;
    }

    public void updateStockRecord(StockRecord stockRecord){
        String sql = "UPDATE stock_record SET item_id = ?, quantity = ?, operator = ?, effective_date = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, stockRecord.getItemId());
            stmt.setInt(2, stockRecord.getQuantity());
            stmt.setString(3, stockRecord.getOperator());
            stmt.setTimestamp(4, new Timestamp(stockRecord.getEffectiveDate().getTime()));
            stmt.setInt(5, stockRecord.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteStockRecord(int id) {
        String sql = "DELETE FROM stock_record WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<StockRecord> getAllStockRecords() {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return stockRecords;
    }

    public List<StockRecord> findByItemId(int itemId) {
        String sql = "SELECT * FROM stock_record where item_id=?";
        List<StockRecord> stockRecords = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ) {
            stmt.setInt(1,itemId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                StockRecord stockRecord = new StockRecord();
                stockRecord.setId(rs.getInt("id"));
                stockRecord.setItemId(rs.getInt("item_id"));
                stockRecord.setQuantity(rs.getInt("quantity"));
                stockRecord.setOperator(rs.getString("operator"));
                stockRecord.setEffectiveDate(rs.getTimestamp("effective_date"));

                stockRecords.add(stockRecord);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return stockRecords;
    }



    //----------------------------------User------------------------------------------------------------------------------


    public void createUser(User user) {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUser(int id){
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public void updateUser(User user) {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUser(int id) {
        String sql = "DELETE FROM user WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public User selectByUserName(String userName) {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }



    //----------------------------------vendor------------------------------------------------------------------------------

    public void createVendor(Vendor vendor) {
        String sql = "INSERT INTO vendor (name, email, phone) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, vendor.getName());
            stmt.setString(2, vendor.getEmail());
            stmt.setString(3, vendor.getPhone());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Vendor getVendor(int id) {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return vendor;
    }

    public void updateVendor(Vendor vendor) {
        String sql = "UPDATE vendor SET name = ?, email = ?, phone = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, vendor.getName());
            stmt.setString(2, vendor.getEmail());
            stmt.setString(3, vendor.getPhone());
            stmt.setInt(4, vendor.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteVendor(int id) {
        String sql = "DELETE FROM vendor WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Vendor> getAllVendors()  {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return vendors;
    }

    //----------------------------------ReorderTracker------------------------------------------------------------------------------

    public void createReorderTracker(ReorderTracker reorderTracker) {
        String sql = "INSERT INTO reorder_tracker (item_id, status, date, vendor_id, error_message) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reorderTracker.getItemId());
            stmt.setInt(2, reorderTracker.getStatus());
            stmt.setTimestamp(3, new Timestamp(reorderTracker.getDate().getTime()));
            stmt.setInt(4, reorderTracker.getVendorId());
            stmt.setString(5, reorderTracker.getErrorMessage());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Retrieve a reorder tracker entry by item ID
    public ReorderTracker getReorderTracker(int itemId) {
        String sql = "SELECT * FROM reorder_tracker WHERE item_id = ?";
        ReorderTracker reorderTracker = null;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, itemId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    reorderTracker = new ReorderTracker();
                    reorderTracker.setItemId(rs.getInt("item_id"));
                    reorderTracker.setStatus(rs.getInt("status"));
                    reorderTracker.setDate(rs.getTimestamp("date"));
                    reorderTracker.setVendorId(rs.getInt("vendor_id"));
                    reorderTracker.setErrorMessage(rs.getString("error_message"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reorderTracker;
    }

    // Update a reorder tracker entry
    public void updateReorderTracker(ReorderTracker reorderTracker) {
        String sql = "UPDATE reorder_tracker SET status = ?, date = ?, vendor_id = ?, error_message = ? WHERE item_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reorderTracker.getStatus());
            stmt.setTimestamp(2, new Timestamp(reorderTracker.getDate().getTime()));
            stmt.setInt(3, reorderTracker.getVendorId());
            stmt.setString(4, reorderTracker.getErrorMessage());
            stmt.setInt(5, reorderTracker.getItemId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Delete a reorder tracker entry by item ID
    public void deleteReorderTracker(int itemId) {
        String sql = "DELETE FROM reorder_tracker WHERE item_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, itemId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Retrieve all reorder tracker entries
    public List<ReorderTracker> getAllReorderTrackers() {
        String sql = "SELECT * FROM reorder_tracker";
        List<ReorderTracker> reorderTrackers = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ReorderTracker reorderTracker = new ReorderTracker();
                reorderTracker.setItemId(rs.getInt("item_id"));
                reorderTracker.setStatus(rs.getInt("status"));
                reorderTracker.setDate(rs.getTimestamp("date"));
                reorderTracker.setVendorId(rs.getInt("vendor_id"));
                reorderTracker.setErrorMessage(rs.getString("error_message"));

                reorderTrackers.add(reorderTracker);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reorderTrackers;
    }

}
