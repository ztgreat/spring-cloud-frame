package com.cloud.member.api.impl;

import com.cloud.member.api.MemberFeignApi;
import com.cloud.member.service.MemberService;

import javax.annotation.Resource;

public class MemberFeignApiFallbackImpl implements MemberFeignApi {


    @Resource
    private MemberService memberService;


    @Override
    public String hello(String message) {
        return "fail";
    }
}
