package com.selected.inventory_dashboard.service.impl;

import com.selected.inventory_dashboard.auth.JwtHelper;
import com.selected.inventory_dashboard.dtovo.req.LoginDto;
import com.selected.inventory_dashboard.dtovo.req.SignupDto;
import com.selected.inventory_dashboard.persistence.dao.UserMapper;
import com.selected.inventory_dashboard.persistence.entity.User;
import com.selected.inventory_dashboard.service.interfaces.AuthService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author: Haiyan Cai
 * @create: 2024-10-27 10:29
 * @description:
 **/
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserMapper userMapper;

    @Override
    public String login(LoginDto loginDto) {

        UsernamePasswordAuthenticationToken usernamePassword =
                new UsernamePasswordAuthenticationToken(loginDto.getUserName(),loginDto.getPassword());

        Authentication authenticate = authenticationManager.authenticate(usernamePassword);

        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("userName or password incorrect");
        }

        String token = JwtHelper.generateToken(loginDto.getUserName());
        return token;
    }

    @Override
    public void signup(SignupDto dto) {
        User user=new User();
        BeanUtils.copyProperties(dto,user);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(dto.getPassword());
        user.setPassword(encodedPassword);
        user.setRoleType(1);

        userMapper.insert(user);
    }
}
