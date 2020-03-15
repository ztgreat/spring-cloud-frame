package com.cloud.gateway.service.impl;


import cn.hutool.core.collection.CollectionUtil;
import com.cloud.gateway.domain.DTO.GatewayMysqlRouteDTO;
import com.cloud.gateway.mapper.GatewayRouteMapper;
import com.cloud.gateway.service.GatewayRouteService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GatewayRouteServiceImpl implements GatewayRouteService {

    @Resource
    private GatewayRouteMapper gatewayRouteMapper;

    @Override
    public List<GatewayMysqlRouteDTO> queryAllRoutes() {

        List<GatewayMysqlRouteDTO> gatewayMysqlRoutes = gatewayRouteMapper.queryAllRoutes();

        if (CollectionUtil.isEmpty(gatewayMysqlRoutes)) {
            return gatewayMysqlRoutes;
        }
        gatewayMysqlRoutes.forEach(gatewayMysqlRoute -> {
            gatewayMysqlRoute.setFilters(gatewayRouteMapper.queryRouteFilterByRouteId(gatewayMysqlRoute.getId()));
            gatewayMysqlRoute.setPredicates(gatewayRouteMapper.queryRoutePredicateByRouteId(gatewayMysqlRoute.getId()));
        });
        return gatewayMysqlRoutes;
    }
}