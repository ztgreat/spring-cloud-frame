package com.cloud.gateway.filter;


/**
 * 网关路由 相关的过滤器
 *
 * @author zhangteng
 */
public interface GatewayStrategyRouteFilter extends GatewayStrategyFilter {

    /**
     * 版本信息
     *
     * @return 版本
     */
    String getVersion();
}