package com.selected.inventory_dashboard.persistence.dao;

import com.selected.inventory_dashboard.persistence.entity.ReorderTracker;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ReorderTrackerMapper {
    int deleteByPrimaryKey(@Param("itemId") Integer itemId, @Param("status") Integer status, @Param("date") Date date);

    int insert(ReorderTracker record);

    ReorderTracker selectByPrimaryKey(@Param("itemId") Integer itemId, @Param("status") Integer status, @Param("date") Date date);

    List<ReorderTracker> selectAll();

    int updateByPrimaryKey(ReorderTracker record);
}