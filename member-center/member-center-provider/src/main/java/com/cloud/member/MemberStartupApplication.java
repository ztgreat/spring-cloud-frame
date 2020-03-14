package com.cloud.member;

import com.cloud.ribbon.annotation.EnableFeignInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.support.SpringMvcContract;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableFeignInterceptor
public class MemberStartupApplication {
    public static void main(String[] args) {
        SpringApplication.run(MemberStartupApplication.class, args);
    }

}
