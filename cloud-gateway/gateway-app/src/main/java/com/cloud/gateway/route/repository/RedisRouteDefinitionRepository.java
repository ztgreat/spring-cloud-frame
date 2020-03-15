//package com.cloud.gateway.route.repository;
//
//import com.alibaba.cloud.nacos.NacosConfigProperties;
//import com.alibaba.fastjson.JSON;
//import com.cloud.gateway.domain.GatewayMysqlRoute;
//import com.cloud.gateway.mapper.GatewayRouteMapper;
//import com.cloud.gateway.route.NacosConfigManager;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
//import org.springframework.cloud.gateway.filter.FilterDefinition;
//import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
//import org.springframework.cloud.gateway.route.RouteDefinition;
//import org.springframework.cloud.gateway.support.NotFoundException;
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.context.ApplicationEventPublisherAware;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.web.util.UriComponentsBuilder;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.net.URI;
//import java.util.*;
//
///**
// * 核心配置类，加载数据库的路由配置信息到redis
// * 将定义好的路由表信息通过此类读写到redis中
// */
//@Slf4j
//public class RedisRouteDefinitionRepository extends AbstractRouteDefinitionRepository implements ApplicationEventPublisherAware, CommandLineRunner {
//
//    public static final String GATEWAY_ROUTES = "gateway:routes";
//
//    private RedisTemplate redisTemplate;
//
//    private GatewayRouteMapper gatewayRouteMapper;
//
//    private ApplicationEventPublisher publisher;
//
//    private NacosConfigProperties nacosConfigProperties;
//
//    private NacosConfigManager nacosConfigManager;
//
//    public RedisRouteDefinitionRepository(NacosConfigProperties nacosConfigProperties, RedisTemplate redisTemplate, GatewayRouteMapper gatewayRouteMapper) {
//        this.redisTemplate = redisTemplate;
//        this.gatewayRouteMapper = gatewayRouteMapper;
//        this.nacosConfigProperties = nacosConfigProperties;
//        this.nacosConfigManager = new NacosConfigManager(this.nacosConfigProperties);
//    }
//
//    @Override
//    public void run(String... args) {
//        loadRouteConfig();
//    }
//
//    @Override
//    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
//        this.publisher = applicationEventPublisher;
//    }
//
//
//    @Override
//    public Flux<RouteDefinition> getRouteDefinitions() {
//        List<RouteDefinition> routeDefinitions = new ArrayList<>();
//        redisTemplate.opsForHash().values(GATEWAY_ROUTES).stream().forEach(routeDefinition -> routeDefinitions.add(JSON.parseObject(routeDefinition.toString(), RouteDefinition.class)));
//        return Flux.fromIterable(routeDefinitions);
//    }
//
//    @Override
//    public Mono<Void> save(Mono<RouteDefinition> route) {
//        return route
//                .flatMap(routeDefinition -> {
//                    redisTemplate.opsForHash().put(GATEWAY_ROUTES, routeDefinition.getId(),
//                            JSON.toJSONString(routeDefinition));
//                    return Mono.empty();
//                });
//    }
//
//    @Override
//    public Mono<Void> delete(Mono<String> routeId) {
//        return routeId.flatMap(id -> {
//            if (redisTemplate.opsForHash().hasKey(GATEWAY_ROUTES, id)) {
//                redisTemplate.opsForHash().delete(GATEWAY_ROUTES, id);
//                return Mono.empty();
//            }
//            return Mono.defer(() -> Mono.error(new NotFoundException("路由文件没有找到: " + routeId)));
//        });
//    }
//
//    @Override
//    public void loadRouteConfig() {
//        //从数据库拿到路由配置
//        List<GatewayMysqlRoute> gatewayRouteList = gatewayRouteMapper.queryAllRoutes();
//
//        log.info("网关配置信息：=====>" + JSON.toJSONString(gatewayRouteList));
//
//        gatewayRouteList.forEach(gatewayRoute -> {
//            RouteDefinition definition = new RouteDefinition();
//            Map<String, String> predicateParams = new HashMap<>(8);
//            PredicateDefinition predicate = new PredicateDefinition();
//            FilterDefinition filterDefinition = new FilterDefinition();
//            Map<String, String> filterParams = new HashMap<>(8);
//
//            URI uri;
//            if (gatewayRoute.getUri().startsWith("http")) {
//                //http地址
//                uri = UriComponentsBuilder.fromHttpUrl(gatewayRoute.getUri()).build().toUri();
//            } else {
//                //注册中心
//                uri = UriComponentsBuilder.fromUriString("lb://" + gatewayRoute.getUri()).build().toUri();
//            }
//
//            definition.setId(gatewayRoute.getId().toString());
//            // 名称是固定的，spring gateway会根据名称找对应的PredicateFactory
//            predicate.setName("Path");
//            predicateParams.put("pattern", gatewayRoute.getPredicates());
//            predicate.setArgs(predicateParams);
//
//            // 名称是固定的, 路径去前缀
//            filterDefinition.setName("StripPrefix");
//            filterParams.put("_genkey_0", gatewayRoute.getFilters().toString());
//            filterDefinition.setArgs(filterParams);
//
//            PredicateDefinition predicate2 = new PredicateDefinition();
//            predicate2.setName("Query");
//
//
//            definition.setPredicates(Arrays.asList(predicate));
//            definition.setFilters(Arrays.asList(filterDefinition));
//            definition.setUri(uri);
//            save(Mono.just(definition)).subscribe();
//        });
//
//        this.publisher.publishEvent(new RefreshRoutesEvent(this));
//    }
//
//
//}
