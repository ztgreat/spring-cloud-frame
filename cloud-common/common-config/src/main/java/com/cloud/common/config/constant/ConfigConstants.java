package com.cloud.common.config.constant;

/**
 * 配置项常量
 */
public interface ConfigConstants {
    /**
     * 是否开启自定义隔离规则
     */
    String CONFIG_RIBBON_ISOLATION_ENABLED = "com.cloud.ribbon.isolation.enabled";

    /**
     * 服务版本
     */
    String CONFIG_RIBBON_ISOLATION_VERSION = "com.cloud.ribbon.isolation.version";

    /**
     * feign rest 配置
     */
    String CONFIG_FEIGN_REST_TEMPLATE = "com.cloud.feign.rest-template";
}
