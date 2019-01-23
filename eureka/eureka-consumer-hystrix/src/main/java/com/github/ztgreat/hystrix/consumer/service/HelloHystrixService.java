package com.github.ztgreat.hystrix.consumer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 对于查询操作，我们可以实现一个 fallback 方法，当请求后端服务出现异常的时候，可以使用 fallback 方法返回的值。
 * fallback 方法的返回值一般是设置的默认值或者来自缓存。
 */
@FeignClient(name = "client01",fallback = com.github.ztgreat.hystrix.consumer.service.fallback.HelloHystrixFallbackService.class )
public interface HelloHystrixService {

    @GetMapping("/hello/")
    String hello(@RequestParam(value = "name") String name);
}
