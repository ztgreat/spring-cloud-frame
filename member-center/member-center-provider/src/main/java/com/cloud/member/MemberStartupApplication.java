package com.cloud.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
//@NacosPropertySource(dataId = "showyu-member-center", autoRefreshed = true)
public class MemberStartupApplication {

    public static void main(String[] args) {
        SpringApplication.run(MemberStartupApplication.class, args);
    }

}
