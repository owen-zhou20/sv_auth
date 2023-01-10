package com.sv.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sv.common.result.R;
import com.sv.model.system.SysRole;
import com.sv.model.vo.AssginRoleVo;
import com.sv.model.vo.SysRoleQueryVo;
import com.sv.system.annotation.Log;
import com.sv.system.enums.BusinessType;
import com.sv.system.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "Role management API")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    // 9. Assign roles for a user
    @Log(title = "Role management", businessType = BusinessType.ASSGIN)
    @PreAuthorize("hasAuthority('bnt.sysUser.assignRole')")
    @ApiOperation("Assign roles for a user")
    @PostMapping("doAssign")
    public R doAssign(@RequestBody AssginRoleVo assginRoleVo){
        sysRoleService.doAssign(assginRoleVo);
        return R.ok();
    }

    // 8. Get roles for a user
    @Log(title = "Role management", businessType = BusinessType.SELECT)
    @PreAuthorize("hasAuthority('bnt.sysUser.assignRole')")
    @ApiOperation("Get roles for a user")
    @GetMapping("/toAssign/{userId}")
    public R toAssign(@PathVariable String userId){
        Map<String,Object> roleMap = sysRoleService.getRolesByUserId(userId);
        return R.ok(roleMap);
    }

    // 7. Batch delete
    @Log(title = "Role management", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.sysRole.remove')")
    @ApiOperation("Batch delete")
    @DeleteMapping("batchRemove")
    public R batchRemove(@RequestBody List<Long> ids){
        boolean isSuccess = sysRoleService.removeByIds(ids);
        if(isSuccess){
            return R.ok();
        }else {
            return R.fail();
        }
    }

    // 6. Modify a role
    @Log(title = "Role management", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAuthority('bnt.sysRole.update')")
    @ApiOperation("Modify a role")
    @PostMapping("update")
    public R updateRole(@RequestBody SysRole sysRole){
        boolean isSuccess = sysRoleService.updateById(sysRole);
        if(isSuccess){
            return R.ok();
        }else {
            return R.fail();
        }
    }

    // 5. Modify a role - select info by role id
    @Log(title = "Role management", businessType = BusinessType.SELECT)
    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    @ApiOperation("Modify a role - select info by role id")
    @PostMapping("findRoleById/{id}")
    public R findRoleById(@PathVariable Long id){
        SysRole sysRole = sysRoleService.getById(id);
        return R.ok(sysRole);
    }


    // 4. Add role
    @Log(title = "Role management", businessType = BusinessType.INSERT)
    @PreAuthorize("hasAuthority('bnt.sysRole.add')")
    @ApiOperation("Add role")
    @PostMapping("save")
    public R saveRole(@RequestBody SysRole sysRole){
        boolean isSuccess = sysRoleService.save(sysRole);
        if(isSuccess) {
            return R.ok();
        } else {
            return R.fail();
        }
    }


    // 3. Pagination select roles
    // page: current page
    // limit: record's number for every page
    @Log(title = "Role management", businessType = BusinessType.SELECT)
    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    @ApiOperation("Pagination select roles")
    @GetMapping("{page}/{limit}")
    public R findPageQueryRole(@PathVariable Long page,
                               @PathVariable Long limit,
                               SysRoleQueryVo sysRoleQueryVo){
        Page<SysRole> pageParam = new Page<>(page,limit);
        IPage<SysRole> pageModel = sysRoleService.selectPage(pageParam, sysRoleQueryVo);
        return R.ok(pageModel);
    }

    // 2. Delete role by ID
    @Log(title = "Role management", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.sysRole.remove')")
    @ApiOperation("Logical delete API")
    @DeleteMapping("remove/{id}")
    public R removeRole(@PathVariable String id){
        // call service
        boolean isSuccess = sysRoleService.removeById(id);
        if(isSuccess) {
            return R.ok();
        } else {
            return R.fail();
        }
    }

    // 1. Select all records
    @Log(title = "Role management", businessType = BusinessType.SELECT)
    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    @ApiOperation("Find all roles API")
    @GetMapping("findAll")
    public R findAllRole(){

        /*try{
            int i = 10/0;
        }catch(Exception e){
            throw new SvException(20001,"Execute Sv Exception");
        }*/

        // call service
        List<SysRole> list = sysRoleService.list();
        return R.ok(list);
    }


}
