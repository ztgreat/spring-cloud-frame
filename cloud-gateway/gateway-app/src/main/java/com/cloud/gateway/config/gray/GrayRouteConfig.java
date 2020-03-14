package com.cloud.gateway.config.gray;

import com.cloud.gateway.route.gray.GrayscaleLoadBalancerRule;
import com.netflix.loadbalancer.IRule;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 灰度路由规则相关配置
 *
 * @author zhangteng
 */
@Configuration
@ConditionalOnProperty(prefix = "cloud.gateway.gray", name = "enabled", havingValue = "true", matchIfMissing = true)
public class GrayRouteConfig {

    @Bean
    IRule rule() {
        return new GrayscaleLoadBalancerRule();
    }
}