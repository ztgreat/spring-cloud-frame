package com.cloud.gateway.route.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;

/**
 * Nacos 动态路由支持
 *
 * @author zhangteng
 */
@Slf4j
public abstract class AbstractRouteDefinitionRepository implements RouteDefinitionRepository {


    public abstract void loadRouteConfig();


}
