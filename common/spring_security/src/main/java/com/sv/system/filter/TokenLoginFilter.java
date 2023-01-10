package com.sv.system.filter;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sv.common.result.R;
import com.sv.common.result.ResultCodeEnum;
import com.sv.common.utils.JwtHelper;
import com.sv.common.utils.ResponseUtil;
import com.sv.model.vo.LoginVo;
import com.sv.system.custom.CustomUser;
import com.sv.system.service.LoginLogService;
import com.sv.common.utils.IpUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    private RedisTemplate redisTemplate;

    private LoginLogService loginLogService;
    public TokenLoginFilter(AuthenticationManager authenticationManager,RedisTemplate redisTemplate, LoginLogService loginLogService) {
        this.setAuthenticationManager(authenticationManager);
        this.setPostOnly(false);
        //指定登录接口及提交方式，可以指定任意路径
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/admin/system/index/login","POST"));
        this.redisTemplate = redisTemplate;
        this.loginLogService = loginLogService;
    }

    // Get username and password for Authentication
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginVo loginVo = new ObjectMapper().readValue(request.getInputStream(), LoginVo.class);
            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(loginVo.getUsername(), loginVo.getPassword());
            return this.getAuthenticationManager().authenticate(authenticationToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Success to authentic
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication auth) throws IOException, ServletException {
        // Get auth entity
        CustomUser customUser = (CustomUser) auth.getPrincipal();

        // Save authorities in Redis
        redisTemplate.opsForValue().set(customUser.getUsername(), JSON.toJSONString(customUser.getAuthorities()));

        // Create token
        String token = JwtHelper.createToken(customUser.getSysUser().getId(), customUser.getSysUser().getUsername());

        // Record login log
        loginLogService.recordLoginLog(customUser.getUsername(),0, IpUtil.getIpAddress(request),"Success to login");

        // return
        Map<String, Object> map = new HashMap<>();
        map.put("token",token);
        ResponseUtil.out(response, R.ok(map));
    }

    // Fail to authentic
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException e) throws IOException, ServletException {
        if(e.getCause() instanceof RuntimeException) {
            ResponseUtil.out(response, R.build(null, 204, e.getMessage()));
        } else {
            ResponseUtil.out(response, R.build(null, ResultCodeEnum.LOGIN_MOBLE_ERROR));
        }
    }


}
