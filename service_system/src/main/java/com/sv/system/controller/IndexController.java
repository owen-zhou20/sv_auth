package com.sv.system.controller;

import com.sv.common.result.R;
import com.sv.common.utils.JwtHelper;
import com.sv.common.utils.MD5;
import com.sv.model.system.SysUser;
import com.sv.model.vo.LoginVo;
import com.sv.system.exception.SvException;
import com.sv.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "User login API")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController {

    @Autowired
    private SysUserService sysUserService;

    //login
    @ApiOperation("login and get token")
    @PostMapping("login")
    public R login(@RequestBody LoginVo loginVo){
        // Get user info by username from DB for login
        SysUser sysUser = sysUserService.getUserInfoByUserName(loginVo.getUsername());

        // Check this sysUser is not exist
        if(sysUser == null){
            throw new SvException(20001,"This user is not exist!");
        }

        // Check the password for this sysUser
        String password = loginVo.getPassword();
        String md5Password = MD5.encrypt(password);
        if(!sysUser.getPassword().equals(md5Password)){
            throw new SvException(20001,"This password is not correct!");
        }

        // Check this user' status is not disable: 0
        if(sysUser.getStatus().intValue() == 0){
            throw new SvException(20001,"This user is forbidden to login!");
        }

        // Create token by user id and user name
        String token = JwtHelper.createToken(sysUser.getId(), sysUser.getUsername());

        // Return token in a map
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        System.out.println("token=======>"+token);
        return R.ok(map);
    }

    // Get user info(user info and user's menus) by token
    @ApiOperation("Get user info(user info and user's menus) by token")
    @GetMapping("info")
    public R info(HttpServletRequest request){
        // Get token from request head
        String token = request.getHeader("token");
        // Get user id and username from token
        String username = JwtHelper.getUsername(token);
        // Get user info(user info and user's menus) by username
        Map<String,Object> map = sysUserService.getUserInfo(username);
        // return map
        return R.ok(map);
    }




}
