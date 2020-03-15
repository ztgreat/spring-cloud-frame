package com.cloud.gateway.route.repository;

import cn.hutool.core.util.StrUtil;
import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.cloud.gateway.route.NacosConfigManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * Nacos 动态路由支持
 *
 * @author zhangteng
 */
@Slf4j
public class NacosRouteDefinitionRepository extends AbstractRouteDefinitionRepository implements ApplicationEventPublisherAware, CommandLineRunner {


    private volatile boolean started;


    //todo 测试 使用
    private static final String NACOS_DATA_ID = "gateway-dev-routes";
    private static final String NACOS_GROUP_ID = "DEFAULT_GROUP";

    private ApplicationEventPublisher publisher;

    private NacosConfigProperties nacosConfigProperties;

    private NacosConfigManager nacosConfigManager;

    public NacosRouteDefinitionRepository(NacosConfigProperties nacosConfigProperties) {
        this.nacosConfigProperties = nacosConfigProperties;
        this.nacosConfigManager = new NacosConfigManager(this.nacosConfigProperties);
        this.started = false;
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
        try {
            String content = nacosConfigManager.getConfigService().getConfig(NACOS_DATA_ID, NACOS_GROUP_ID, 5000);
            List<RouteDefinition> routeDefinitions = getListByStr(content);
            return Flux.fromIterable(routeDefinitions);
        } catch (NacosException e) {
            log.error("getRouteDefinitions by nacos error", e);
        }
        return Flux.fromIterable(Collections.<RouteDefinition>emptyList());
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return null;
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return null;
    }

    private List<RouteDefinition> getListByStr(String content) {
        if (StrUtil.isNotEmpty(content)) {
            return JSONObject.parseArray(content, RouteDefinition.class);
        }
        return new ArrayList<>(0);
    }

    @Override
    public synchronized void loadRouteConfig() {

        if(started){
            return;
        }
        this.started = true;
        try {
            addListener();
        } catch (NacosException e) {
            log.error("nacos Route Rule addListener fail", e);
            this.started = false;
        }
    }

    /**
     * 添加Nacos监听
     */
    private void addListener() throws NacosException {

        nacosConfigManager.getConfigService().addListener(NACOS_DATA_ID, NACOS_GROUP_ID, new Listener() {
            @Override
            public Executor getExecutor() {
                return null;
            }

            @Override
            public void receiveConfigInfo(String configInfo) {
                log.info("nacos-Listener-receiveConfigInfo:[{}]", configInfo);
                publisher.publishEvent(new RefreshRoutesEvent(this));
            }
        });

    }


}
