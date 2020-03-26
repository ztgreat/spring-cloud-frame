package com.cloud.gateway.filter;


import cn.hutool.core.util.StrUtil;
import com.cloud.common.config.constant.CommonConstant;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public abstract class AbstractGatewayStrategyRouteFilter implements GatewayStrategyRouteFilter {

    @Override
    public int getOrder() {
        return 8000;
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
        return chain.filter(finalExchange);
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
}