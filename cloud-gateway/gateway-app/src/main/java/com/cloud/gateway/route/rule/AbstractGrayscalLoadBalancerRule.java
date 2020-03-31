package com.cloud.gateway.route.rule;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.cloud.common.config.constant.CommonConstant;
import com.netflix.loadbalancer.ZoneAvoidanceRule;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;


/**
 * 灰度路由选择 规则
 * 根据指定的灰度版本来选择
 *
 * @author zhangteng
 */
@Slf4j
@Getter
public abstract class AbstractGrayscalLoadBalancerRule extends ZoneAvoidanceRule {

    /**
     * asc 正序 反之desc 倒叙
     */
    protected boolean asc = true;

    /**
     * 筛选想要的值
     *
     * @param instances 服务实例
     * @param version   版本信息
     * @return 根据版本筛选后的服务
     */
    List<Instance> buildVersion(List<Instance> instances, String version) {
        //进行按版本分组排序
        Map<String, List<Instance>> versionMap = getInstanceByScreen(instances);
        if (versionMap.isEmpty()) {
            log.warn("no instance in service {}", version);
        }
        //如果version 未传值使用最低版本服务
        if (StrUtil.isBlank(version)) {
            if (isAsc()) {
                version = getFirst(versionMap.keySet());
            } else {
                version = getLast(versionMap.keySet());
            }
        }

        List<Instance> instanceList = versionMap.get(version);

        //没有任何数据
        if (CollectionUtil.isEmpty(instanceList)) {
            return Collections.emptyList();
        }
        return instanceList;
    }

    /**
     * 根据version 组装一个map key value  对应 version List<Instance>
     *
     * @param instances 服务实例
     */
    private Map<String, List<Instance>> getInstanceByScreen(List<Instance> instances) {

        Map<String, List<Instance>> versionMap = new HashMap<>(instances.size());
        instances.forEach(instance -> {
            String version = instance.getMetadata().get(CommonConstant.METADATA_VERSION);
            List<Instance> versions = versionMap.get(version);
            if (versions == null) {
                versions = new ArrayList<>();
            }
            versions.add(instance);
            versionMap.put(version, versions);
        });
        return versionMap;
    }

    /**
     * 获取第一个值
     */
    private String getFirst(Set<String> keys) {
        List<String> list = sortVersion(keys);
        return list.get(0);
    }

    /**
     * 获取最后一个值
     */
    private String getLast(Set<String> keys) {
        List<String> list = sortVersion(keys);
        return list.get(list.size() - 1);
    }

    /**
     * 根据版本排序
     */
    private List<String> sortVersion(Set<String> keys) {
        List<String> list = new ArrayList<>(keys);
        Collections.sort(list);
        return list;
    }
}