package com.cloud.order;

import com.cloud.ribbon.annotation.EnableFeignInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableFeignInterceptor
public class OrderStartupApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderStartupApplication.class, args);
    }

}
