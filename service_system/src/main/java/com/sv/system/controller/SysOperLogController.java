package com.sv.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sv.common.result.R;
import com.sv.model.system.SysOperLog;
import com.sv.model.vo.SysOperLogQueryVo;
import com.sv.system.annotation.Log;
import com.sv.system.enums.BusinessType;
import com.sv.system.service.OperLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "SysOperLog management", tags = "SysOperLog management")
@RestController
@RequestMapping(value="/admin/system/sysOperLog")
public class SysOperLogController {

    @Autowired
    private OperLogService operLogService;

    // Pagination select operation Log
    //@PreAuthorize("hasAuthority('bnt.sysOperLog.list')")
    @Log(title = "Operation Log management", businessType = BusinessType.SELECT)
    @ApiOperation(value = "Pagination select operation Log")
    @GetMapping("{page}/{limit}")
    public R index(
            @ApiParam(name = "page", value = "current page", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "limit records in every page", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "sysOperLogVo", value = "select condition VO", required = false)
            SysOperLogQueryVo sysOperLogQueryVo) {
        IPage<SysOperLog> pageModel = operLogService.selectPage(page,limit,sysOperLogQueryVo);
        return R.ok(pageModel);
    }

}
