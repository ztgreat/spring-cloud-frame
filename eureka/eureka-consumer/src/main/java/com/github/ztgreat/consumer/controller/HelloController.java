package com.github.ztgreat.consumer.controller;

import com.github.ztgreat.consumer.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/hello")
@RestController
public class HelloController {

    @Autowired
    HelloService helloService;

    @RequestMapping(value = "/{name}",method = RequestMethod.GET)
    public String index(@PathVariable("name") String name) {
        return helloService.hello(name);
    }

}