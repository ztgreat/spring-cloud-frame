package com.cloud.member.api;

import com.cloud.member.api.impl.MemberFeignApiFallbackImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "member-provider", fallback = MemberFeignApiFallbackImpl.class)
public interface MemberFeignApi {

    @GetMapping(value = "/test/{message}")
    String hello(@PathVariable("message") String message);


}
