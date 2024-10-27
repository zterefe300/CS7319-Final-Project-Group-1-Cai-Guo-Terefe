package com.selected.inventory_dashboard.auth;

import com.selected.inventory_dashboard.persistence.dao.UserMapper;
import com.selected.inventory_dashboard.persistence.entity.User;
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
    private UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectByUserName(username);
        if (user==null){
            throw new UsernameNotFoundException("user does not exist");
        }
        MySysUserDetails mySysUserDetails=new MySysUserDetails(user);
        return mySysUserDetails;
    }
}
