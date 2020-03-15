package com.cloud.gateway.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.gateway.domain.DTO.GatewayMysqlRouteDTO;
import com.cloud.gateway.domain.GatewayMysqlRoute;
import com.cloud.gateway.domain.GatewayMysqlRouteFilter;
import com.cloud.gateway.domain.GatewayMysqlRoutePredicate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 网关路由mapper
 *
 * @author zhangteng
 */
@Mapper
public interface GatewayRouteMapper extends BaseMapper<GatewayMysqlRoute> {

    /**
     * 查询所有有效的路由信息
     * 不包含 filter 和 Predicate
     *
     * @return List<GatewayMysqlRouteDTO>
     */
    List<GatewayMysqlRouteDTO> queryAllRoutes();

    /**
     * 获取指定路由的 filter 信息
     *
     * @param routeId 路由id
     * @return filter 信息
     */
    List<GatewayMysqlRouteFilter> queryRouteFilterByRouteId(@Param("routeId") Integer routeId);

    /**
     * 获取指定路由的 Predicate 信息
     *
     * @param routeId 路由id
     * @return Predicate 信息
     */
    List<GatewayMysqlRoutePredicate> queryRoutePredicateByRouteId(@Param("routeId") Integer routeId);

}