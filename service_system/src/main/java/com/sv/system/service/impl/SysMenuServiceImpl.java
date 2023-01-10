package com.sv.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sv.model.system.SysMenu;
import com.sv.model.system.SysRoleMenu;
import com.sv.model.vo.AssginMenuVo;
import com.sv.model.vo.RouterVo;
import com.sv.system.exception.SvException;
import com.sv.system.mapper.SysMenuMapper;
import com.sv.system.mapper.SysRoleMenuMapper;
import com.sv.system.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sv.system.utils.MenuHelper;
import com.sv.system.utils.RouterHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author Owen
 * @since 2022-12-17
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    // 1. Menu list (tree data)
    @Override
    public List<SysMenu> findNodes() {
        // Get all menus
        List<SysMenu> sysMenuList = baseMapper.selectList(null);
        // Change format and return
        List<SysMenu> resultList = MenuHelper.bulidTree(sysMenuList);
        return resultList;
    }

    // 5. Delete a menu without submenus
    @Override
    public boolean removeMenuById(String id) {
        // Select sub-menus for this menu by id
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0){ // has sub-menus
            throw new SvException(20001,"Can't delete a menu which has sub-menus!");
        }else {
            int isSuccess = baseMapper.deleteById(id);
            if (isSuccess == 1){
                return true;
            }else {
                return false;
            }
        }
    }

    // 6. Get menus for a role
    @Override
    public List<SysMenu> findMenuByRoleId(String roleId) {
        // Get all menus(status = 1)
        QueryWrapper<SysMenu> wrapperMenu = new QueryWrapper<>();
        wrapperMenu.eq("status",1);
        List<SysMenu> menuList = baseMapper.selectList(wrapperMenu);

        // Get user's menus for a user by id
        QueryWrapper<SysRoleMenu> wrapperRoleMenu = new QueryWrapper<>();
        wrapperRoleMenu.eq("role_id",roleId);
        List<SysRoleMenu> roleMenus = sysRoleMenuMapper.selectList(wrapperRoleMenu);
        // Get user menus' ids
        List<String> roleMenuIds = new ArrayList<>();
        for (SysRoleMenu sysRoleMenu : roleMenus) {
            String menuId = sysRoleMenu.getMenuId();
            roleMenuIds.add(menuId);
        }

        // Set isSelect==true for those user menus in all menus list
        for (SysMenu sysMenu : menuList) {
            if(roleMenuIds.contains(sysMenu.getId())){
                sysMenu.setSelect(true);
            } else {
                sysMenu.setSelect(false);
            }
        }

        // Format to tree data use bulidTree() in MenuHelper class
        List<SysMenu> sysMenuList = MenuHelper.bulidTree(menuList);
        return sysMenuList;
    }

    // 7. Assign menus for a role
    @Override
    public void doAssign(AssginMenuVo assginMenuVo) {
        // Delete all menu data about this role by role id
        QueryWrapper<SysRoleMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",assginMenuVo.getRoleId());
        sysRoleMenuMapper.delete(wrapper);

        // Get menu list and role id and insert into table sys_role_menu in DB
        List<String> menuIdList = assginMenuVo.getMenuIdList();
        for (String menuId : menuIdList) {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setMenuId(menuId);
            sysRoleMenu.setRoleId(assginMenuVo.getRoleId());
            sysRoleMenuMapper.insert(sysRoleMenu);
        }
    }

    // 8. Get user menu auth by user id
    @Override
    public List<RouterVo> getUserMenuLisy(String userId) {
        // admin(id==1) is super admin and has all auth
        List<SysMenu> sysMenuList = null;
        System.out.println("================>>>>"+("1".equals(userId)));
        if("1".equals(userId)){
            QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
            wrapper.eq("status",1);
            wrapper.orderByAsc("sort_value");
            sysMenuList = baseMapper.selectList(wrapper);
        } else {
            // Get user auth for the user not admin(id==1) by user id
            sysMenuList = baseMapper.findMenuListUserId(userId);
        }
        // Build tree data
        List<SysMenu> sysMenuTreeList = MenuHelper.bulidTree(sysMenuList);
        // Format to router data as front need
        List<RouterVo> routerVoList = RouterHelper.buildRouters(sysMenuTreeList);
        return routerVoList;
    }

    // 9. Get user button auth by user id
    @Override
    public List<String> getUserButtonList(String userId) {
        List<SysMenu> sysMenuList = null;
        // admin(id==1) is super admin and has all auth
        if("1".equals(userId)){
            sysMenuList = baseMapper.selectList(new QueryWrapper<SysMenu>().eq("status", 1));
        } else{
            // Get user auth for the user not admin(id==1) by user id
            sysMenuList = baseMapper.findMenuListUserId(userId);
        }
        // return permissionList which has all user perms
        List<String> permissionList = new ArrayList<>();
        for (SysMenu sysMenu : sysMenuList) {
            if(sysMenu.getType() == 2){
                String perms = sysMenu.getPerms();
                permissionList.add(perms);
            }
        }
        return permissionList;
    }
}
