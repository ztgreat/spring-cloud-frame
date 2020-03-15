package com.cloud.gateway.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.cloud.common.core.annotation.Token;
import com.cloud.common.core.domain.Result;
import com.cloud.gateway.domain.DTO.UserDTO;
import com.cloud.gateway.domain.JwtModel;
import com.cloud.gateway.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

/**
 * 描述: 认证接口
 */
@RestController
@Slf4j
@RequestMapping("/auth")
public class AuthController {

    private ObjectMapper objectMapper;


    @Value("${org.my.jwt.effective-time}")
    private String effectiveTime;

    public AuthController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * 登陆认证接口
     *
     * @param userDTO
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDTO) throws Exception {
        ArrayList<String> roleIdList = new ArrayList<>(1);
        roleIdList.add("role_test_1");
        JwtModel jwtModel = new JwtModel("test", roleIdList);
        int effectivTimeInt = Integer.valueOf(effectiveTime.substring(0, effectiveTime.length() - 1));
        String effectivTimeUnit = effectiveTime.substring(effectiveTime.length() - 1, effectiveTime.length());
        String jwt = null;
        switch (effectivTimeUnit) {
            case "s": {
                //秒
                jwt = JwtUtil.createJWT("test", "test", objectMapper.writeValueAsString(jwtModel), effectivTimeInt * 1000L);
                break;
            }
            case "m": {
                //分钟
                jwt = JwtUtil.createJWT("test", "test", objectMapper.writeValueAsString(jwtModel), effectivTimeInt * 60L * 1000L);
                break;
            }
            case "h": {
                //小时
                jwt = JwtUtil.createJWT("test", "test", objectMapper.writeValueAsString(jwtModel), effectivTimeInt * 60L * 60L * 1000L);
                break;
            }
            case "d": {
                //小时
                jwt = JwtUtil.createJWT("test", "test", objectMapper.writeValueAsString(jwtModel), effectivTimeInt * 24L * 60L * 60L * 1000L);
                break;
            }
        }
        return Result.success("认证成功", jwt);
    }

    /**
     * 为授权提示
     */
    @GetMapping("/unauthorized")
    @SentinelResource("unauthorized")
    public Result unauthorized() {
        return Result.fail("未认证,请重新登陆");
    }

    /**
     * jwt 检查注解测试 测试
     *
     * @return
     */
    @GetMapping("/testJwtCheck")
    @Token
    public Result testJwtCheck(@RequestHeader("Authorization") String token, @RequestParam("name") @Valid String name) {

        return Result.success("请求成功", "请求成功:" + name);

    }
}
