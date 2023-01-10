package com.sv.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sv.model.system.SysOperLog;
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
public interface OperLogMapper extends BaseMapper<SysOperLog> {


}
