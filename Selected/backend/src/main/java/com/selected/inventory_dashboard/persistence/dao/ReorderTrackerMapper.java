package com.selected.inventory_dashboard.persistence.dao;

import com.selected.inventory_dashboard.persistence.entity.ReorderTracker;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ReorderTrackerMapper {
    int deleteByPrimaryKey(@Param("itemId") Integer itemId, @Param("status") Integer status, @Param("date") Integer date);

    int insert(ReorderTracker record);

    ReorderTracker selectByPrimaryKey(@Param("itemId") Integer itemId, @Param("status") Integer status, @Param("date") Integer date);

    List<ReorderTracker> selectAll();

    int updateByPrimaryKey(ReorderTracker record);
}