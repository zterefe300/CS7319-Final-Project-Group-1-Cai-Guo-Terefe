package com.selected.inventory_dashboard.persistence.dao;

import com.selected.inventory_dashboard.persistence.entity.StockRecord;
import java.util.List;

public interface StockRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StockRecord record);

    StockRecord selectByPrimaryKey(Integer id);

    List<StockRecord> selectAll();

    int updateByPrimaryKey(StockRecord record);

    /**
     * get stockRecords by itemId
     */
    List<StockRecord> findByItemId(int itemId);
}