package com.cloud.gateway.controller;

import com.cloud.common.core.domain.Result;
import com.cloud.gateway.domain.DTO.GatewayMysqlRouteDTO;
import com.cloud.gateway.route.repository.AbstractRouteDefinitionRepository;
import com.cloud.gateway.service.GatewayRouteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * 1.直接在数据库添加路由配置信息，手动刷新，使配置信息立即生效；
 * <p>
 * 2.前端页面增、删、改路由配置信息，并使配置信息立即生效；
 */
@RestController
@RequestMapping("/route")
public class RouteController {

    @Resource
    private GatewayRouteService gatewayRouteService;


    @Resource
    private AbstractRouteDefinitionRepository abstractRouteDefinitionRepository;

    @GetMapping("/routes")
    public List<GatewayMysqlRouteDTO> routes() {
        return gatewayRouteService.queryAllRoutes();
    }


    @GetMapping("/refresh")
    public Result refresh() {
        abstractRouteDefinitionRepository.loadRouteConfig();
        return Result.success();
    }
}