package com.github.ztgreat.hystrix.consumer.service;

import com.github.ztgreat.hystrix.consumer.service.fallback.HelloHystrixFallbackService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "client01",fallback = com.github.ztgreat.hystrix.consumer.service.fallback.HelloHystrixFallbackService.class )
public interface HelloHystrixService {

    @GetMapping("/hello/")
    String hello(@RequestParam(value = "name") String name);
}
