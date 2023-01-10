package com.sv.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sv.model.system.SysOperLog;
import com.sv.model.vo.SysOperLogQueryVo;

public interface OperLogService {

    // save log into DB
    void saveSysLog(SysOperLog sysOperLog);

    // Pagination select operation Log
    IPage<SysOperLog> selectPage(Long page, Long limit, SysOperLogQueryVo sysOperLogQueryVo);
}
