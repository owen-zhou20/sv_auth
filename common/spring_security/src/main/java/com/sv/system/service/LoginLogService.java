package com.sv.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sv.model.system.SysLoginLog;
import com.sv.model.vo.SysLoginLogQueryVo;

public interface LoginLogService {

    // Record login log
    void recordLoginLog(String username, Integer status, String ipaddr, String message);

    // Pagination select login Log
    IPage<SysLoginLog> selectPage(Long page, Long limit, SysLoginLogQueryVo sysLoginLogQueryVo);
}
