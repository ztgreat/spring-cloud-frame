package com.cloud.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
//@NacosPropertySource(dataId = "showyu-member-center", autoRefreshed = true)
public class OrderStartupApplication {

    public static void main(String[] args) {
        SpringApplication.run(PassportStartupApplication.class, args);
    }

}
