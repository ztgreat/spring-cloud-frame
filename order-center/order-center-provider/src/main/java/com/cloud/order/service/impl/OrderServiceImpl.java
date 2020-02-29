package com.cloud.order.service.impl;

import com.cloud.member.dto.Member;
import com.cloud.order.remote.MemberRemote;
import com.cloud.order.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class OrderServiceImpl implements OrderService {


    @Resource
    private MemberRemote memberRemote;


    @Override
    public Member hello(String message) {
        Member member = new Member();
        member.setName(message);
        return memberRemote.hello(member);
    }
}
