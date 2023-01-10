package com.sv.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sv.model.system.SysUser;
import com.sv.model.vo.SysUserQueryVo;

import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Owen
 * @since 2022-12-14
 */
public interface SysUserService extends IService<SysUser> {

    // 1. Pagination select roles
    IPage<SysUser> selectPage(Page<SysUser> pageParam, SysUserQueryVo sysUserQueryVo);

    // 6. Modify a user status
    boolean updateStatus(String id, Integer status);

    // Get user info by username from DB for login
    SysUser getUserInfoByUserName(String username);

    // Get user info(user info and user's menus) by token
    Map<String, Object> getUserInfo(String username);
}
