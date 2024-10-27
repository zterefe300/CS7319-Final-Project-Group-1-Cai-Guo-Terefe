package com.selected.inventory_dashboard.dtovo.req;

import lombok.Data;

import java.util.Date;

/**
 * @author: Haiyan Cai
 * @create: 2024-10-27 13:31
 * @description:
 **/
@Data
public class SignupDto {

    private String userName;

    private String password;

    private String firstName;

    private String lastName;
}
