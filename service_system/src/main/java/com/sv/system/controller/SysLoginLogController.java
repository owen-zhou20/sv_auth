package com.sv.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sv.common.result.R;
import com.sv.model.system.SysLoginLog;
import com.sv.model.vo.SysLoginLogQueryVo;
import com.sv.system.annotation.Log;
import com.sv.system.enums.BusinessType;
import com.sv.system.service.LoginLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "SysLoginLog management", tags = "SysLoginLog management")
@RestController
@RequestMapping("/admin/system/sysLoginLog")
public class SysLoginLogController {
    @Autowired
    private LoginLogService loginLogService;

    // Pagination select login Log
    //@PreAuthorize("hasAuthority('bnt.sysLoginLog.list')")
    //@Log(title = "Login Log management", businessType = BusinessType.SELECT)
    @ApiOperation("Pagination select login Log")
    @GetMapping("{page}/{limit}")
    public R index(@PathVariable Long page,
                   @PathVariable Long limit,
                   SysLoginLogQueryVo sysLoginLogQueryVo){
        IPage<SysLoginLog> pageModel = loginLogService.selectPage(page,limit,sysLoginLogQueryVo);
        return R.ok(pageModel);

    }

}
