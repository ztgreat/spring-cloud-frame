package com.github.ztgreat.hystrix.consumer.service.fallback;

import com.github.ztgreat.hystrix.consumer.service.HelloHystrixService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class HelloHystrixFallbackService implements HelloHystrixService {

    @Override
    public String hello(@RequestParam(value = "name") String name) {
        return "Hello World!--Fallback";
    }
}
