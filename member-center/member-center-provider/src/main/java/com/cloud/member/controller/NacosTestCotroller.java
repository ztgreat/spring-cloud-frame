package com.cloud.member.controller;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/server")
@Slf4j
@RefreshScope
public class NacosTestCotroller {


    @Value("${nacos.yaml.age:1}")
    private String age;

    @SentinelResource("hello")
    @GetMapping("/hello")
    public String hello(@RequestParam String name) {
        log.info("invoked name = " + name + " age = " + age);
        return "hello " + name + " age = " + age;
    }


}
