package com.cloud.order.remote;

import com.cloud.order.remote.impl.MemberRemoteFallbackImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "member-center", fallback = MemberRemoteFallbackImpl.class)
public interface MemberRemote {

    @GetMapping(value = "/test/{message}")
    String hello(@PathVariable("message") String message);


}
