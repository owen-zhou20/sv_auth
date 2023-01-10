package com.sv.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sv.model.system.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author Owen
 * @since 2022-12-17
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    // Get user auth for the user not admin(id==1) by user id
    List<SysMenu> findMenuListUserId(@Param("userid") String userId);
}
