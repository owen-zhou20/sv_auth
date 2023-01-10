package com.sv.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sv.model.system.SysRole;
import com.sv.model.system.SysUserRole;
import com.sv.model.vo.AssginRoleVo;
import com.sv.model.vo.SysRoleQueryVo;
import com.sv.system.mapper.SysRoleMapper;
import com.sv.system.mapper.SysUserRoleMapper;
import com.sv.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    // 3. Pagination select roles
    @Override
    public IPage<SysRole> selectPage(Page<SysRole> pageParam, SysRoleQueryVo sysRoleQueryVo) {
        IPage<SysRole> pageModel = baseMapper.selectPage(pageParam,sysRoleQueryVo);

        return pageModel;
    }

    // 8. Get roles for a user
    @Override
    public Map<String, Object> getRolesByUserId(String userId) {
        // Get all roles
        List<SysRole> roles = baseMapper.selectList(null);
        // Get roles for a user by user id
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        List<SysUserRole> userRolesList = sysUserRoleMapper.selectList(wrapper);
        // Get all role ids from userRoles
        List<String> userRoleIds = new ArrayList<>();
        for (SysUserRole userRole : userRolesList) {
            String roleId = userRole.getRoleId();
            userRoleIds.add(roleId);
        }
        // put roles and userRoleIds into returnMap and return
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("allRoles",roles);
        returnMap.put("userRoleIds",userRoleIds);
        return returnMap;
    }

    // 9. Assign roles for a user
    @Override
    public void doAssign(AssginRoleVo assginRoleVo) {
        // Delete all roles data about this user by user id
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",assginRoleVo.getUserId());
        sysUserRoleMapper.delete(wrapper);
        // Get role list and user id and insert into table sys_user_role in DB
        List<String> roleIdList = assginRoleVo.getRoleIdList();
        for (String roleId : roleIdList) {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(assginRoleVo.getUserId());
            userRole.setRoleId(roleId);
            sysUserRoleMapper.insert(userRole);
        }
    }
}
