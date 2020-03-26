package com.cloud.gateway.filter;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;

/**
 * 网关自定义过滤器
 *
 * @author zhangteng
 */
public interface GatewayStrategyFilter extends GlobalFilter, Ordered {

}