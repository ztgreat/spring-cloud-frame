package com.cloud.gateway.service;


import com.cloud.gateway.domain.DTO.GatewayMysqlRouteDTO;

import java.util.List;

/**
 * gateway 路由服务
 *
 * @author zhangteng
 */
public interface GatewayRouteService {

    /**
     * 查询所有有效的路由信息
     * 包含 filter 和 Predicate
     *
     * @return List<GatewayMysqlRouteDTO>
     */
    List<GatewayMysqlRouteDTO> queryAllRoutes();

}