package com.sv.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sv.model.system.SysUser;
import com.sv.model.vo.RouterVo;
import com.sv.model.vo.SysUserQueryVo;
import com.sv.system.mapper.SysUserMapper;
import com.sv.system.service.SysMenuService;
import com.sv.system.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Owen
 * @since 2022-12-14
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysMenuService sysMenuService;

    // 1. Pagination select roles
    @Override
    public IPage<SysUser> selectPage(Page<SysUser> pageParam, SysUserQueryVo sysUserQueryVo) {
        return baseMapper.selectPage(pageParam, sysUserQueryVo);
    }

    // 6. Modify a user status
    @Override
    public boolean updateStatus(String id, Integer status) {
        // Get user info by id
        SysUser sysUser = baseMapper.selectById(id);
        // Set status for user
        sysUser.setStatus(status);
        // Update user info in DB
        int isSuccess = baseMapper.updateById(sysUser);
        if(isSuccess == 1){
            return true;
        } else {
            return false;
        }
    }

    // 7. Get user info by username from DB for login
    @Override
    public SysUser getUserInfoByUserName(String username) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        SysUser sysUser = baseMapper.selectOne(wrapper);
        return sysUser;
    }

    // Get user info(user info and user's menus) by token
    @Override
    public Map<String, Object> getUserInfo(String username) {
        // Get username info by username
        SysUser sysUser = this.getUserInfoByUserName(username);
        // Get user menu auth
        List<RouterVo> routerVoList = sysMenuService.getUserMenuLisy(sysUser.getId());
        // Get user button auth
        List<String> permsList = sysMenuService.getUserButtonList(sysUser.getId());
        // return map
        Map<String, Object> result = new HashMap<>();
        result.put("name",username);
        result.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        result.put("roles","[admin]");
        // user menu auth
        result.put("routers",routerVoList);
        // user button auth
        result.put("buttons",permsList);
        return result;
    }
}
