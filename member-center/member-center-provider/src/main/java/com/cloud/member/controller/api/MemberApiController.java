package com.cloud.member.controller.api;

import com.cloud.member.service.MemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class MemberApiController {


    @Resource
    private MemberService memberService;

    @GetMapping(value = "/test/{message}")
    public String test(@PathVariable("message") String message) {
        return memberService.hello(message);
    }


}
