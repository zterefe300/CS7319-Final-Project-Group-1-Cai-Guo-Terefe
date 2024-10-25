package com.arch.selected.dao;

import com.arch.selected.entity.StorkRecord;
import java.util.List;

public interface StorkRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StorkRecord record);

    StorkRecord selectByPrimaryKey(Integer id);

    List<StorkRecord> selectAll();

    int updateByPrimaryKey(StorkRecord record);
}