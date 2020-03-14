package com.cloud.order.remote.impl;

import com.cloud.member.dto.Member;
import com.cloud.order.remote.MemberRemote;
import org.springframework.stereotype.Service;

@Service
public class MemberRemoteFallbackImpl implements MemberRemote {


    @Override
    public Member hello(Member message) {
        message.setName("fail");
        return message;
    }

    @Override
    public Member hello2(Member message) {
        message.setName("fail2");
        return message;
    }
}
