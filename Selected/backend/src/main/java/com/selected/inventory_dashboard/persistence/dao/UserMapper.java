package com.selected.inventory_dashboard.persistence.dao;

import com.selected.inventory_dashboard.persistence.entity.User;
import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);


    User selectByUserName(String userName);
}