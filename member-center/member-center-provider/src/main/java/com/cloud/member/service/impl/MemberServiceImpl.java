package com.cloud.member.service.impl;

import com.cloud.member.service.MemberService;
import org.springframework.stereotype.Service;


@Service
public class MemberServiceImpl implements MemberService {


    @Override
    public String hello(String message) {
        return message;
    }
}
