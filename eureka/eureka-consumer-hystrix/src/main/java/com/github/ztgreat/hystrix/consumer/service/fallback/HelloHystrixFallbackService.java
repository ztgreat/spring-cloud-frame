package com.github.ztgreat.consumer.service.fallback;

import com.github.ztgreat.consumer.service.HelloHystrixService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class HelloHystrixFallbackService implements HelloHystrixService {

    @Override
    public String hello(@RequestParam(value = "name") String name) {
        return "Hello World!--Fallback";
    }
}
