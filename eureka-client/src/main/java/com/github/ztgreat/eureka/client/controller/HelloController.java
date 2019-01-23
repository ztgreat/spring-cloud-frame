package com.github.ztgreat.eureka.client.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String hello(@RequestParam(value = "name") String name) {
        return "Hello world "+name;
    }

}