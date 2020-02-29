package com.cloud.member.controller;

import com.cloud.member.api.MemberFeignApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NacosConsumerFeignController {


    @Autowired
    private MemberFeignApi memberFeignApi;

    @GetMapping(value = "/test/hi")
    public String test() {
        return memberFeignApi.hello("Hi Feign");
    }


}
