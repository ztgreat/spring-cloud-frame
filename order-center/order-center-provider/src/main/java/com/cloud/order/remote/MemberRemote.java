package com.cloud.order.remote;

import com.cloud.member.dto.Member;
import com.cloud.order.remote.impl.MemberRemoteFallbackImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "member-center", fallback = MemberRemoteFallbackImpl.class)
public interface MemberRemote {

    @PostMapping(value = "/test")
    Member hello(Member message);


}
