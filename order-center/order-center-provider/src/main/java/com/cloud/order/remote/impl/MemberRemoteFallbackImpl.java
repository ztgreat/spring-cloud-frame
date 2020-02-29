package com.cloud.order.remote.impl;

import com.cloud.order.remote.MemberRemote;
import org.springframework.stereotype.Service;

@Service
public class MemberRemoteFallbackImpl implements MemberRemote {


    @Override
    public String hello(String message) {
        return "fail";
    }
}
