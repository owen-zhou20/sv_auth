package com.sv.system.controller;


import com.sv.common.result.R;
import com.sv.model.system.SysMenu;
import com.sv.model.vo.AssginMenuVo;
import com.sv.system.annotation.Log;
import com.sv.system.enums.BusinessType;
import com.sv.system.service.SysMenuService;
import io.jsonwebtoken.lang.Strings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author Owen
 * @since 2022-12-17
 */
@Api(tags = "Menu management API")
@RestController
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    // 1. Menu list (tree data)
    @Log(title = "Menu management", businessType = BusinessType.SELECT)
    @PreAuthorize("hasAuthority('bnt.sysMenu.list')")
    @ApiOperation("Menu list")
    @GetMapping("findNodes")
    public R findNodes(){
        List<SysMenu> list = sysMenuService.findNodes();
        return R.ok(list);
    }

    // 2. Add a menu
    @Log(title = "Menu management", businessType = BusinessType.INSERT)
    @PreAuthorize("hasAuthority('bnt.sysMenu.add')")
    @ApiOperation("Add menu")
    @PostMapping("save")
    public R save(@RequestBody SysMenu menu){
        boolean isSuccess = sysMenuService.save(menu);
        if(isSuccess) {
            return R.ok();
        } else {
            return R.fail();
        }
    }

    // 3. Modify a menu - select info by menu id
    @Log(title = "Menu management", businessType = BusinessType.SELECT)
    @PreAuthorize("hasAuthority('bnt.sysMenu.update')")
    @ApiOperation("Modify a menu - select info by menu id")
    @GetMapping("findNode/{id}")
    public R findNode(@PathVariable String id){
        SysMenu menu = sysMenuService.getById(id);
        if(menu!=null){
            return R.ok(menu);
        }else {
            return R.fail("Modify a menu - select info by menu id is fail!");
        }
    }

    // 4. Modify a menu
    @Log(title = "Menu management", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAuthority('bnt.sysMenu.update')")
    @ApiOperation("Modify a menu")
    @PostMapping("update")
    public R update(@RequestBody SysMenu menu){
        boolean isSuccess = sysMenuService.updateById(menu);
        if(isSuccess) {
            return R.ok();
        } else {
            return R.fail();
        }
    }

    // 5. Delete a menu without sub-menus
    @Log(title = "Menu management", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.sysMenu.remove')")
    @ApiOperation("Delete a menu without sub-menus")
    @DeleteMapping("remove/{id}")
    public R delete(@PathVariable String id){
        boolean isSuccess = sysMenuService.removeMenuById(id);
        if(isSuccess) {
            return R.ok();
        } else {
            return R.fail();
        }
    }

    // 6. Get menus for a role
    @Log(title = "Menu management", businessType = BusinessType.SELECT)
    @PreAuthorize("hasAuthority('bnt.sysRole.assignAuth')")
    @ApiOperation("Get menus for a role")
    @GetMapping("/toAssign/{roleId}")
    public R toAssign(@PathVariable String roleId){
        List<SysMenu> list = sysMenuService.findMenuByRoleId(roleId);
        return R.ok(list);
    }

    // 7. Assign menus for a role
    @Log(title = "Menu management", businessType = BusinessType.ASSGIN)
    @PreAuthorize("hasAuthority('bnt.sysRole.assignAuth')")
    @ApiOperation("Assign menus for a role")
    @PostMapping("doAssign")
    public R doAssign(@RequestBody AssginMenuVo assginMenuVo){
        sysMenuService.doAssign(assginMenuVo);
        return R.ok();
    }



}

