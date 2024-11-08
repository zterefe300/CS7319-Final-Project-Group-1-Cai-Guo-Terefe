package com.inventory_dashboard.u_backend.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: Haiyan Cai
 * @create: 2024-10-27 13:48
 * @description:
 **/
@Getter
@AllArgsConstructor
public enum RoleTypeEnum {
    ADMIN(0,"admin"),
    EMPLOYEE(1,"employee");


    private Integer code;
    private String roleName;
}
