package com.cloud.order.controller;

import com.cloud.member.dto.Member;
import com.cloud.order.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class OrderController {


    @Resource
    private OrderService orderService;

    @GetMapping(value = "/test/{message}")
    @ResponseBody
    public Member test(@PathVariable("message") String message) {
        return orderService.hello(message);
    }


}
