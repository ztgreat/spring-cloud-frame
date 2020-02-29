package com.cloud.order.service.impl;

import com.cloud.order.remote.MemberRemote;
import com.cloud.order.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class OrderServiceImpl implements OrderService {


    @Resource
    private MemberRemote memberRemote;


    @Override
    public String hello(String message) {
        return memberRemote.hello(message);
    }
}
