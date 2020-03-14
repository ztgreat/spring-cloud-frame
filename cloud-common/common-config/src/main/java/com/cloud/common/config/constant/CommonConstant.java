package com.cloud.common.config.constant;

/**
 * 全局公共常量
 */
public interface CommonConstant {
    /**
     * 项目版本号
     */
    String PROJECT_VERSION = "1.1.0";

    /**
     * token请求头名称
     */
    String TOKEN_HEADER = "Authorization";


    String BEARER_TYPE = "Bearer";


    /**
     * 租户id参数
     */
    String TENANT_ID_PARAM = "tenantId";


    /**
     * 日志链路追踪id信息头
     */
    String TRACE_ID_HEADER = "x-traceId-header";
    /**
     * 日志链路追踪id日志标志
     */
    String LOG_TRACE_ID = "traceId";
    /**
     * 负载均衡策略-版本号 信息头
     */
    String SERVICE_VERSION = "service-version";

    /**
     * 灰度版本信息
     */
    String GRAY_VERSION = "gray-version";
    /**
     * 注册中心元数据 版本号
     */
    String METADATA_VERSION = "version";
}
