package com.cloud.gateway.mapper;


import com.cloud.gateway.domain.GatewayMysqlRoute;

import java.util.List;

public interface GatewayRouteMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GatewayMysqlRoute record);

    int insertSelective(GatewayMysqlRoute record);

    GatewayMysqlRoute selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GatewayMysqlRoute record);

    int updateByPrimaryKey(GatewayMysqlRoute record);

    List<GatewayMysqlRoute> queryAllRoutes();
}