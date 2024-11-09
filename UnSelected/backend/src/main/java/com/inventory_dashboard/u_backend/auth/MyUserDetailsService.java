package com.inventory_dashboard.u_backend.auth;

import com.inventory_dashboard.u_backend.db.Dao;
import com.inventory_dashboard.u_backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

/**
 * @author: Haiyan Cai
 * @create: 2024-10-27 10:23
 * @description:
 **/
@Component
public class MyUserDetailsService implements UserDetailsService {


    @Autowired
    private Dao dao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        try {
            user = dao.selectByUserName(username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (user==null){
            throw new UsernameNotFoundException("user does not exist");
        }
        MySysUserDetails mySysUserDetails=new MySysUserDetails(user);
        return mySysUserDetails;
    }
}
