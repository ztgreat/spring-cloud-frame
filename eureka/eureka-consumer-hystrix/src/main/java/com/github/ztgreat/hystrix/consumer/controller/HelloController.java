package com.github.ztgreat.hystrix.consumer.controller;

import com.github.ztgreat.hystrix.consumer.service.HelloHystrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/hello")
@RestController
public class HelloController {

    @Autowired
    HelloHystrixService helloService;

    @RequestMapping(value = "/{name}",method = RequestMethod.GET)
    public String index(@PathVariable("name") String name) {
        return helloService.hello(name);
    }

}