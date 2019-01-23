package com.github.ztgreat.hystrix.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class SpringCloudEurekaConsumerHystrixApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudEurekaConsumerHystrixApplication.class, args);
    }

}