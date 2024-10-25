package com.arch.selected.dao;

import com.arch.selected.entity.Vendor;
import java.util.List;

public interface VendorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Vendor record);

    Vendor selectByPrimaryKey(Integer id);

    List<Vendor> selectAll();

    int updateByPrimaryKey(Vendor record);
}