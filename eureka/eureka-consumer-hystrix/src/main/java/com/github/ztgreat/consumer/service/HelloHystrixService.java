package com.github.ztgreat.consumer.service;

import com.github.ztgreat.consumer.service.fallback.HelloHystrixFallbackService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "client01",fallback = HelloHystrixFallbackService.class)
public interface HelloHystrixService {

    @GetMapping("/hello/")
    String hello(@RequestParam(value = "name") String name);
}
