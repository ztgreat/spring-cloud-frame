package com.cloud.gateway.route.filter;

import cn.hutool.core.util.StrUtil;
import com.cloud.common.config.constant.CommonConstant;
import com.cloud.gateway.properties.RouteProperties;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.config.LoadBalancerProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.LoadBalancerClientFilter;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

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
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 获取路由信息
        String version = getVersion();
        ServerHttpRequest.Builder requestBuilder = exchange.getRequest().mutate();
        // 通过过滤器设置路由Header头部信息，并全链路传递到服务端
        addVersionHeader(requestBuilder, CommonConstant.METADATA_VERSION, version);
        ServerHttpRequest newRequest = requestBuilder.build();
        ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();
        ServerWebExchange extensionExchange = extendFilter(newExchange, chain);
        ServerWebExchange finalExchange = extensionExchange != null ? extensionExchange : newExchange;

        return super.filter(finalExchange, chain);
    }

    /**
     * 版本信息
     *
     * @return 版本
     */
    protected String getVersion() {
        return "1.0.0";
    }

    /**
     * 添加 header 信息
     *
     * @param requestBuilder requestBuilder
     * @param headerName     headerName
     * @param headerValue    headerValue
     */
    protected void addVersionHeader(ServerHttpRequest.Builder requestBuilder, String headerName, String headerValue) {
        if (StrUtil.isEmpty(headerName)) {
            return;
        }
        requestBuilder.headers(headers -> headers.add(CommonConstant.METADATA_VERSION, headerValue));
    }

    /**
     * 扩展点
     *
     * @param exchange 原始 exchange
     * @param chain    原始 chain
     * @return 新 ServerWebExchange
     */
    protected ServerWebExchange extendFilter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return null;
    }


    @Override
    protected ServiceInstance choose(ServerWebExchange exchange) {

        if (!(this.loadBalancer instanceof RibbonLoadBalancerClient)) {
            return super.choose(exchange);
        }
        RibbonLoadBalancerClient client = (RibbonLoadBalancerClient) this.loadBalancer;
        HttpHeaders headers = exchange.getRequest().getHeaders();
        String version = headers.getFirst(CommonConstant.METADATA_VERSION);

        if (StrUtil.isEmpty(version)) {
            return super.choose(exchange);
        }
        String serviceId = ((URI) exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR)).getHost();
        RouteProperties build = RouteProperties.builder().version(version).serverName(serviceId).build();
        //这里使用服务ID 和 version 做为选择服务实例的key
        //TODO 这里也可以根据实际业务情况做自己的对象封装
        return client.choose(serviceId, build);

    }
}