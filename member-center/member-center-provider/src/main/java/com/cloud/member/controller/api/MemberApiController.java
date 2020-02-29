package com.cloud.member.controller.api;

import com.cloud.member.dto.Member;
import com.cloud.member.service.MemberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class MemberApiController {


    @Resource
    private MemberService memberService;

    @PostMapping(value = "/test")
    public Member test(@RequestBody Member member) {
        return memberService.hello(member.getName());
    }


}
