package com.sv.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sv.model.system.SysRole;
import com.sv.model.vo.AssginRoleVo;
import com.sv.model.vo.SysRoleQueryVo;

import java.util.Map;

public interface SysRoleService extends IService<SysRole> {
    // 3. Pagination select roles
    IPage<SysRole> selectPage(Page<SysRole> pageParam, SysRoleQueryVo sysRoleQueryVo);

    // 8. Get roles for a user
    Map<String, Object> getRolesByUserId(String userId);

    // 9. Assign roles for a user
    void doAssign(AssginRoleVo assginRoleVo);
}
