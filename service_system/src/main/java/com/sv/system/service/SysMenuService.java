package com.sv.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sv.model.system.SysMenu;
import com.sv.model.vo.AssginMenuVo;
import com.sv.model.vo.RouterVo;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author Owen
 * @since 2022-12-17
 */
public interface SysMenuService extends IService<SysMenu> {

    // 1. Menu list (tree)
    List<SysMenu> findNodes();

    // 5. Delete a menu without sub-menus
    boolean removeMenuById(String id);

    // 6. Get menus for a role
    List<SysMenu> findMenuByRoleId(String roleId);

    // 7. Assign menus for a role
    void doAssign(AssginMenuVo assginMenuVo);

    // 8. Get user menu auth by user id
    List<RouterVo> getUserMenuLisy(String id);

    // 9. Get user button auth by user id
    List<String> getUserButtonList(String id);
}
