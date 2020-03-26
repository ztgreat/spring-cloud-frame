package com.cloud.gateway.config;


import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.ribbon.NacosRibbonClientConfiguration;
import com.cloud.gateway.decorator.NacosServerListDecorator;
import com.netflix.loadbalancer.ServerList;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
//@AutoConfigureAfter(NacosRibbonClientConfiguration.class)
//public class NacosServerListConfiguration {
//
//
//    @Bean
//    public ServerList<?> ribbonServerList(NacosDiscoveryProperties nacosDiscoveryProperties) {
//        NacosServerListDecorator serverList = new NacosServerListDecorator(nacosDiscoveryProperties);
//        return serverList;
//    }
//}