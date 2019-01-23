package com.github.ztgreat.consumer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "client01")
public interface HelloService {

    @GetMapping("/hello/")
    String hello(@RequestParam(value = "name") String name);
}
