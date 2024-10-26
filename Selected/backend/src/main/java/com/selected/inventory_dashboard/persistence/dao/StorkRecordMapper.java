package com.selected.inventory_dashboard.persistence.dao;

import com.selected.inventory_dashboard.persistence.entity.StorkRecord;

import java.util.List;

public interface StorkRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StorkRecord record);

    StorkRecord selectByPrimaryKey(Integer id);

    List<StorkRecord> selectAll();

    int updateByPrimaryKey(StorkRecord record);
}