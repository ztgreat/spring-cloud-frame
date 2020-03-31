package com.cloud.gateway.route.rule;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.ribbon.ExtendBalancer;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.cloud.gateway.properties.RouteProperties;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zhangteng
 */
@Slf4j
public class NacosLoadBalancerRule extends AbstractLoadBalancerRule {

    @Resource
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    /**
     * gateway 特殊性。需要设置key值内容知道你要转发的服务名称 key已经在filter内设置了key值。
     *
     * @param key loadBalancerKey
     * @return 上游服务
     */
    @Override
    public Server choose(Object key) {

        if (!(key instanceof RouteProperties)) {
            return super.choose(key);
        }
        try {
            RouteProperties routeProperties = (RouteProperties) key;
            String version = routeProperties.getVersion();
            String clusterName = this.nacosDiscoveryProperties.getClusterName();
            NamingService namingService = this.nacosDiscoveryProperties.namingServiceInstance();
            List<Instance> instances = namingService.selectInstances(routeProperties.getServerName(), true);

            if (CollectionUtil.isEmpty(instances)) {
                log.warn("no instance in service {}", routeProperties.getServerName());
                return null;
            } else {
                List<Instance> instancesToChoose = buildVersion(instances, version);
                //进行cluster-name分组筛选
                // TODO 思考如果cluster-name 节点全部挂掉。是不是可以请求其他的分组的服务？可以根据情况在定制一份规则出来
                if (StrUtil.isNotBlank(clusterName)) {
                    List<Instance> sameClusterInstances = instancesToChoose.stream().filter((instancex) -> Objects.equals(clusterName, instancex.getClusterName())).collect(Collectors.toList());
                    if (!CollectionUtil.isEmpty(sameClusterInstances)) {
                        instancesToChoose = sameClusterInstances;
                    } else {
                        log.warn("A cross-cluster call occurs，name = {}, clusterName = {}, instance = {}", routeProperties.getServerName(), clusterName, instances);
                    }
                }
                //按nacos权重获取。这个是NacosRule的代码copy 过来 没有自己实现权重随机。这个权重是nacos控制台服务的权重设置
                // 如果业务上有自己特殊的业务。可以自己定制规则，黑白名单，用户是否是灰度用户，测试账号。等等一些自定义设置
                if (CollectionUtil.isEmpty(instancesToChoose)) {
                    return null;
                }
                Instance instance = ExtendBalancer.getHostByRandomWeight2(instancesToChoose);
                if (Objects.isNull(instance)) {
                    return null;
                }
                return new NacosServer(instance);
            }
        } catch (Exception e) {
            log.warn("GrayRule error", e);
            return null;
        }
    }
}