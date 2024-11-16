package com.unselected.inventory_dashboard.auth;

import com.unselected.inventory_dashboard.db.Dao;
import com.unselected.inventory_dashboard.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

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
        User user = user = dao.selectByUserName(username);
        if (user==null){
            throw new UsernameNotFoundException("user does not exist");
        }
        MySysUserDetails mySysUserDetails=new MySysUserDetails(user);
        return mySysUserDetails;
    }
}
