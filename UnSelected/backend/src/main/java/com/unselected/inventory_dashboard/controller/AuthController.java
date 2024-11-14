package com.unselected.inventory_dashboard.controller;

import com.unselected.inventory_dashboard.auth.JwtHelper;
import com.unselected.inventory_dashboard.db.Dao;
import com.unselected.inventory_dashboard.dto.LoginDto;
import com.unselected.inventory_dashboard.dto.SignupDto;
import com.unselected.inventory_dashboard.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.Objects;

/**
 * @author: Haiyan Cai
 * @create: 2024-10-27 10:32
 * @description:
 **/

@RestController
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private Dao dao;


    @PostMapping("/api/auth/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto){

        UsernamePasswordAuthenticationToken usernamePassword =
                new UsernamePasswordAuthenticationToken(loginDto.getUserName(),loginDto.getPassword());

        Authentication authenticate = authenticationManager.authenticate(usernamePassword);

        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("userName or password incorrect");
        }

        String token = JwtHelper.generateToken(loginDto.getUserName());

        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    @PostMapping("/api/auth/signup")
    public ResponseEntity<Void> signup(@RequestBody SignupDto signupDto) throws SQLException {
        User user=new User();
        BeanUtils.copyProperties(signupDto,user);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(signupDto.getPassword());
        user.setPassword(encodedPassword);
        user.setRoleType(1);
        dao.createUser(user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
