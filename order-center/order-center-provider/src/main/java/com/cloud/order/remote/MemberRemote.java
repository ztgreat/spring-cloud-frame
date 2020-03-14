package com.cloud.order.remote;

import com.cloud.common.config.constant.CommonConstant;
import com.cloud.member.dto.Member;
import com.cloud.order.remote.impl.MemberRemoteFallbackImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "member-center", path = "/member-center", fallback = MemberRemoteFallbackImpl.class)
public interface MemberRemote {

    @PostMapping(value = "/test", headers = {CommonConstant.SERVICE_VERSION + "=1.0.0"})
    Member hello(Member message);


    @PostMapping(value = "/test", headers = {CommonConstant.SERVICE_VERSION + "=2.0.0"})
    Member hello2(Member message);


}
