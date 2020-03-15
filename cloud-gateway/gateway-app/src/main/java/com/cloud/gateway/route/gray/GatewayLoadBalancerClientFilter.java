package com.cloud.gateway.route.gray;

import cn.hutool.core.util.StrUtil;
import com.cloud.common.config.constant.CommonConstant;
import com.cloud.gateway.properties.GrayRouteProperties;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.config.LoadBalancerProperties;
import org.springframework.cloud.gateway.filter.LoadBalancerClientFilter;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerClient;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

/**
 * 灰度负载均衡 过滤器
 *
 * @author zhangteng
 */
@Component
public class GatewayLoadBalancerClientFilter extends LoadBalancerClientFilter {

    public GatewayLoadBalancerClientFilter(LoadBalancerClient loadBalancer, LoadBalancerProperties properties) {
        super(loadBalancer, properties);
    }

    @Override
    protected ServiceInstance choose(ServerWebExchange exchange) {

        if (!(this.loadBalancer instanceof RibbonLoadBalancerClient)) {
            return super.choose(exchange);
        }
        RibbonLoadBalancerClient client = (RibbonLoadBalancerClient) this.loadBalancer;
        HttpHeaders headers = exchange.getRequest().getHeaders();
        String version = headers.getFirst(CommonConstant.GRAY_VERSION);

        if (StrUtil.isEmpty(version)) {
            return super.choose(exchange);
        }
        String serviceId = ((URI) exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR)).getHost();
        GrayRouteProperties build = GrayRouteProperties.builder().version(version).serverName(serviceId).build();
        //这里使用服务ID 和 version 做为选择服务实例的key
        //TODO 这里也可以根据实际业务情况做自己的对象封装
        return client.choose(serviceId, build);

    }
}