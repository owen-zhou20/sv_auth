package com.sv.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sv.model.system.SysLoginLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author Owen
 * @since 2022-12-14
 */


@Repository
public interface LoginLogMapper extends BaseMapper<SysLoginLog> {


}
