package com.selected.inventory_dashboard.persistence.dao;

import com.selected.inventory_dashboard.persistence.entity.Vendor;
import java.util.List;

public interface VendorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Vendor record);

    Vendor selectByPrimaryKey(Integer id);

    List<Vendor> selectAll();

    int updateByPrimaryKey(Vendor record);
}