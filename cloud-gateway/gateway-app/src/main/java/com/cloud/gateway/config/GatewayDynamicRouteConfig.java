package com.cloud.gateway.config;

import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.cloud.gateway.route.repository.AbstractRouteDefinitionRepository;
import com.cloud.gateway.route.repository.RedisRouteDefinitionRepository;
import com.cloud.gateway.service.GatewayRouteService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;


/**
 * 动态路由相关配置
 *
 * @author zhangteng
 */
@Configuration
@ConditionalOnProperty(prefix = "cloud.gateway.dynamicRoute", name = "enabled", havingValue = "true", matchIfMissing = true)
public class GatewayDynamicRouteConfig {


    @Resource
    private NacosConfigProperties nacosConfigProperties;

//    @Bean
//    public AbstractRouteDefinitionRepository nacosRouteDefinitionRepository() {
//        return new NacosRouteDefinitionRepository(nacosConfigProperties);
//    }


    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private GatewayRouteService gatewayRouteService;

    @Bean
    public AbstractRouteDefinitionRepository redisRouteDefinitionRepository() {
        return new RedisRouteDefinitionRepository(nacosConfigProperties, redisTemplate, gatewayRouteService);
    }

}