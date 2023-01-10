package com.sv.system.utils;

import com.sv.model.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

public class MenuHelper {

    // Build tree data
    public static List<SysMenu> bulidTree(List<SysMenu> sysMenuList) {
        // result list
        List<SysMenu> trees = new ArrayList<>();
        // traverse sysMenuList
        for (SysMenu sysMenu : sysMenuList) {
            // find the entry root node(parentid == 0)
            if(Long.valueOf(sysMenu.getParentId())==0){
                // use recursive put all sub nodes into the parent node(parentid == 0)
                trees.add(findChildren(sysMenu,sysMenuList));
            }
        }
        return trees;
    }

    // Recursive from entry(sysMenu)/root(parentid == 0) node, find sub nodes (id = sub nodes's parentid)
    private static SysMenu findChildren(SysMenu sysMenu, List<SysMenu> sysMenuList) {
        // format sub nodes list
        sysMenu.setChildren(new ArrayList<SysMenu>());
        // use traverse and recursive to find sub nodes
        for (SysMenu menuNode : sysMenuList) {
            //
            String id = sysMenu.getId();
            Long parentId = Long.valueOf(menuNode.getParentId());
            if(Long.parseLong(id)==parentId){
                sysMenu.getChildren().add(findChildren(menuNode,sysMenuList));
            }
        }
        return sysMenu;
    }


}
