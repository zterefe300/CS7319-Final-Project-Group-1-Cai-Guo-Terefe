package com.inventory_dashboard.u_backend.auth;

import com.inventory_dashboard.u_backend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author: Haiyan Cai
 * @create: 2024-10-27 10:25
 * @description:
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MySysUserDetails implements UserDetails {


    private Integer id;

    private String username;

    private String password;
    private List<Integer> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public MySysUserDetails(User sysUser) {
        this.id = sysUser.getId();
        this.username = sysUser.getUserName();
        this.password = sysUser.getPassword();
        this.roles=new ArrayList<>();
        roles.add(RoleTypeEnum.EMPLOYEE.getCode());
        if(sysUser.getRoleType()== RoleTypeEnum.ADMIN.getCode()){
            roles.add(RoleTypeEnum.ADMIN.getCode());
        }

    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
