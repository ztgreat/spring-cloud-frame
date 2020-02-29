package com.cloud.member.service.impl;

import com.cloud.member.dto.Member;
import com.cloud.member.service.MemberService;
import org.springframework.stereotype.Service;


@Service
public class MemberServiceImpl implements MemberService {


    @Override
    public Member hello(String message) {
        Member member = new Member();
        member.setName(message);
        return member;
    }
}
