package com.selected.inventory_dashboard.controller;

import com.selected.inventory_dashboard.dtovo.req.LoginDto;
import com.selected.inventory_dashboard.dtovo.req.SignupDto;
import com.selected.inventory_dashboard.dtovo.res.LoginRes;
import com.selected.inventory_dashboard.service.interfaces.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Haiyan Cai
 * @create: 2024-10-27 10:32
 * @description:
 **/

@RestController
public class AuthController {
    @Autowired
    private AuthService authService;


    @PostMapping("/api/auth/login")
    public ResponseEntity<LoginRes> login(@RequestBody LoginDto loginDto){

        String token=  authService.login(loginDto);
        LoginRes res=new LoginRes();
        res.setToken(token);
        res.setUserName(loginDto.getUserName());
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("/api/auth/signup")
    public ResponseEntity<Void> signup(@RequestBody SignupDto signupDto) {
        authService.signup(signupDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
