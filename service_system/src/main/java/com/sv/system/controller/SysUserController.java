package com.sv.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sv.common.result.R;
import com.sv.common.utils.MD5;
import com.sv.model.system.SysUser;
import com.sv.model.vo.SysUserQueryVo;
import com.sv.system.annotation.Log;
import com.sv.system.enums.BusinessType;
import com.sv.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author Owen
 * @since 2022-12-14
 */
@Api(tags = "User management API")
@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    // 1. Pagination select users
    @Log(title = "User management", businessType = BusinessType.SELECT)
    @PreAuthorize("hasAuthority('bnt.sysUser.list')")
    @ApiOperation("User list")
    @GetMapping("/{page}/{limit}")
    public R list(@PathVariable Long page,
                  @PathVariable Long limit,
                  SysUserQueryVo sysUserQueryVo){
        // page entity
        Page<SysUser> pageParam = new Page<>(page,limit);
        // user service to get result
        IPage<SysUser> pageModel = sysUserService.selectPage(pageParam,sysUserQueryVo);
        return R.ok(pageModel);
    }

    // 2. Add a user
    @Log(title = "User management", businessType = BusinessType.INSERT)
    @PreAuthorize("hasAuthority('bnt.sysUser.add')")
    @ApiOperation("Save a user")
    @PostMapping("save")
    public R save(@RequestBody SysUser user){
        // Use MD5 to encrypt the password for the user
        String encrypt = MD5.encrypt(user.getPassword());
        user.setPassword(encrypt);

        boolean isSuccess = sysUserService.save(user);
        if(isSuccess) {
            return R.ok();
        } else {
            return R.fail();
        }
    }

    // 3. Modify a user - select info by user id
    @Log(title = "User management", businessType = BusinessType.SELECT)
    @PreAuthorize("hasAuthority('bnt.sysUser.update')")
    @ApiOperation("Modify a user - select info by user id")
    @GetMapping("getUser/{id}")
    public R getUser(@PathVariable String id){
        SysUser user = sysUserService.getById(id);
        return R.ok(user);
    }

    // 4. Modify a user
    @Log(title = "User management", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAuthority('bnt.sysUser.update')")
    @ApiOperation("Modify a user")
    @PostMapping("update")
    public R update(@RequestBody SysUser user){
        boolean isSuccess = sysUserService.updateById(user);
        if(isSuccess) {
            return R.ok();
        } else {
            return R.fail();
        }
    }

    // 5. Delete a user
    @Log(title = "User management", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.sysUser.remove')")
    @ApiOperation("Delete a user")
    @DeleteMapping("remove/{id}")
    public R delete(@PathVariable String id){
        boolean isSuccess = sysUserService.removeById(id);
        if(isSuccess) {
            return R.ok();
        } else {
            return R.fail();
        }
    }

    // 6. Modify a user status
    @Log(title = "User management", businessType = BusinessType.STATUS)
    @PreAuthorize("hasAuthority('bnt.sysUser.update')")
    @ApiOperation("Modify a user status")
    @GetMapping("updataStatus/{id}/{status}")
    public R updateStatus(@PathVariable String id,
                          @PathVariable Integer status){
        boolean isSuccess = sysUserService.updateStatus(id,status);
        if(isSuccess) {
            return R.ok();
        } else {
            return R.fail();
        }
    }

}

