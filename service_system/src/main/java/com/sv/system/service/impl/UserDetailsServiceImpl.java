package com.sv.system.service.impl;

import com.sv.model.system.SysUser;
import com.sv.system.custom.CustomUser;
import com.sv.system.service.SysMenuService;
import com.sv.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.getUserInfoByUserName(username);
        if(sysUser == null){
            throw new UsernameNotFoundException("This user is not exist!");
        }
        if(sysUser.getStatus().intValue() == 0) {
            throw new RuntimeException("This user is forbidden to login!");
        }
        // Get user auth by user id
        List<String> userPermsList = sysMenuService.getUserButtonList(sysUser.getId());
        // Format to security data
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String perm : userPermsList) {
            authorities.add(new SimpleGrantedAuthority(perm.trim()));
        }
        return new CustomUser(sysUser, authorities);
    }
}
