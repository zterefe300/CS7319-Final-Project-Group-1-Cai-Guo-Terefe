package com.selected.inventory_dashboard.persistence.dao;

import com.selected.inventory_dashboard.persistence.entity.reorderTracker;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface reorderTrackerMapper {
    int deleteByPrimaryKey(@Param("itemId") Integer itemId, @Param("status") Integer status, @Param("date") Integer date);

    int insert(reorderTracker record);

    reorderTracker selectByPrimaryKey(@Param("itemId") Integer itemId, @Param("status") Integer status, @Param("date") Integer date);

    List<reorderTracker> selectAll();

    int updateByPrimaryKey(reorderTracker record);
}