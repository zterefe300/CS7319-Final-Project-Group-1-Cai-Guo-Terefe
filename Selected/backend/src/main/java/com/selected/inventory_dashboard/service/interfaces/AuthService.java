package com.selected.inventory_dashboard.service.interfaces;

import com.selected.inventory_dashboard.dtovo.req.LoginDto;
import com.selected.inventory_dashboard.dtovo.req.SignupDto;

/**
 * @author: Haiyan Cai
 * @create: 2024-10-27 10:29
 * @description:
 **/

public interface AuthService {

    String login(LoginDto dto);
    void signup(SignupDto dto);

}
