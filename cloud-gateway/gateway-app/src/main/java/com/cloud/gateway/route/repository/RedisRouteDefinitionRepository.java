package com.cloud.gateway.route.repository;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.fastjson.JSON;
import com.cloud.gateway.domain.DTO.GatewayMysqlRouteDTO;
import com.cloud.gateway.domain.GatewayMysqlRouteFilter;
import com.cloud.gateway.domain.GatewayMysqlRoutePredicate;
import com.cloud.gateway.route.NacosConfigManager;
import com.cloud.gateway.service.GatewayRouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.*;

/**
 * 核心配置类，加载数据库的路由配置信息到redis
 * 将定义好的路由表信息通过此类读写到redis中
 */
@Slf4j
public class RedisRouteDefinitionRepository extends AbstractRouteDefinitionRepository implements ApplicationEventPublisherAware, CommandLineRunner {

    /**
     * 网关路由 redis key
     */
    public static final String GATEWAY_ROUTES = "gateway:routes";

    private RedisTemplate redisTemplate;

    private GatewayRouteService gatewayRouteService;

    private ApplicationEventPublisher publisher;

    private NacosConfigProperties nacosConfigProperties;

    private NacosConfigManager nacosConfigManager;

    public RedisRouteDefinitionRepository(NacosConfigProperties nacosConfigProperties, RedisTemplate redisTemplate, GatewayRouteService gatewayRouteService) {
        this.redisTemplate = redisTemplate;
        this.gatewayRouteService = gatewayRouteService;
        this.nacosConfigProperties = nacosConfigProperties;
        this.nacosConfigManager = new NacosConfigManager(this.nacosConfigProperties);
    }

    @Override
    public void run(String... args) {
        loadRouteConfig();
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }


    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        List<RouteDefinition> routeDefinitions = new ArrayList<>();
        redisTemplate.opsForHash().values(GATEWAY_ROUTES).stream().forEach(routeDefinition -> routeDefinitions.add(JSON.parseObject(routeDefinition.toString(), RouteDefinition.class)));
        if (CollectionUtil.isEmpty(routeDefinitions)) {
            loadRouteConfig();
            redisTemplate.opsForHash().values(GATEWAY_ROUTES).stream().forEach(routeDefinition -> routeDefinitions.add(JSON.parseObject(routeDefinition.toString(), RouteDefinition.class)));
        }

        return Flux.fromIterable(routeDefinitions);
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route
                .flatMap(routeDefinition -> {
                    redisTemplate.opsForHash().put(GATEWAY_ROUTES, routeDefinition.getId(),
                            JSON.toJSONString(routeDefinition));
                    return Mono.empty();
                });
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return routeId.flatMap(id -> {
            if (redisTemplate.opsForHash().hasKey(GATEWAY_ROUTES, id)) {
                redisTemplate.opsForHash().delete(GATEWAY_ROUTES, id);
                return Mono.empty();
            }
            return Mono.defer(() -> Mono.error(new NotFoundException("路由文件没有找到: " + routeId)));
        });
    }

    @Override
    public void loadRouteConfig() {
        //从数据库拿到路由配置
        List<GatewayMysqlRouteDTO> gatewayRouteList = gatewayRouteService.queryAllRoutes();

        log.info("网关配置信息：=====>" + JSON.toJSONString(gatewayRouteList));

        gatewayRouteList.forEach(gatewayRoute -> {
            RouteDefinition definition = new RouteDefinition();
            URI uri;
            if (gatewayRoute.getUri().startsWith("http")) {
                //http地址
                uri = UriComponentsBuilder.fromHttpUrl(gatewayRoute.getUri()).build().toUri();
            } else {
                //注册中心
                uri = UriComponentsBuilder.fromUriString(gatewayRoute.getUri()).build().toUri();
            }
            definition.setId(gatewayRoute.getId().toString());
            definition.setOrder(gatewayRoute.getSort());
            definition.setUri(uri);
            List<PredicateDefinition> predicateDefinitions = new ArrayList<>();
            List<FilterDefinition> filterDefinitions = new ArrayList<>();
            setGatewayRouteFilterOrPredicate(gatewayRoute, filterDefinitions, predicateDefinitions);
            definition.setPredicates(predicateDefinitions);
            definition.setFilters(filterDefinitions);

            save(Mono.just(definition)).subscribe();
        });

        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }

    private void setGatewayRouteFilterOrPredicate(GatewayMysqlRouteDTO gatewayRoute, List<FilterDefinition> filterDefinitions, List<PredicateDefinition> predicateDefinitions) {

        Map<String, Map<String, String>> mapFilters = new HashMap<>(8);
        Map<String, Map<String, String>> mapPredicates = new HashMap<>(8);
        for (GatewayMysqlRouteFilter gatewayMysqlRouteFilter : gatewayRoute.getFilters()) {
            addToMap(mapFilters, gatewayMysqlRouteFilter.getName(), gatewayMysqlRouteFilter.getArgsName(), gatewayMysqlRouteFilter.getArgsValue());
        }
        for (GatewayMysqlRoutePredicate filter : gatewayRoute.getPredicates()) {
            addToMap(mapPredicates, filter.getName(), filter.getArgsName(), filter.getArgsValue());
        }
        for (Map.Entry<String, Map<String, String>> entry : mapFilters.entrySet()) {
            FilterDefinition filterDefinition = new FilterDefinition();
            filterDefinition.setName(entry.getKey());
            filterDefinition.setArgs(entry.getValue());
            filterDefinitions.add(filterDefinition);
        }
        for (Map.Entry<String, Map<String, String>> entry : mapPredicates.entrySet()) {
            PredicateDefinition predicateDefinition = new PredicateDefinition();
            predicateDefinition.setName(entry.getKey());
            predicateDefinition.setArgs(entry.getValue());
            predicateDefinitions.add(predicateDefinition);
        }

    }

    private void addToMap(Map<String, Map<String, String>> map, String name, String argsName, String argsValue) {
        Map<String, String> args = map.get(name);
        if (Objects.isNull(args)) {
            args = new HashMap<>(8);
            map.put(name, args);
        }
        args.put(argsName, argsValue);
    }


}
