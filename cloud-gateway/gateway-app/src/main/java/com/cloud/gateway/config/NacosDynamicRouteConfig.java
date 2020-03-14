package com.cloud.gateway.config;

import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.cloud.gateway.route.NacosRouteDefinitionRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.filter.LoadBalancerClientFilter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 动态路由相关配置
 *
 * @author zhangteng
 */
@Configuration
@ConditionalOnProperty(prefix = "cloud.gateway.dynamicRoute", name = "enabled", havingValue = "true")
public class NacosDynamicRouteConfig {

    @Resource
    private ApplicationEventPublisher publisher;

    /**
     * Nacos实现方式
     */
    @Configuration
    @ConditionalOnProperty(prefix = "cloud.gateway.dynamicRoute", name = "dataType", havingValue = "nacos", matchIfMissing = true)
    public class NacosDynRoute {
        @Resource
        private NacosConfigProperties nacosConfigProperties;

        @Bean
        public NacosRouteDefinitionRepository nacosRouteDefinitionRepository() {
            return new NacosRouteDefinitionRepository(publisher, nacosConfigProperties);
        }
    }
}