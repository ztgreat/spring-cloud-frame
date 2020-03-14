package com.cloud.common.config.context;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * web请求策略Holder
 */
public class WebRequestContextHolder {
    private static final ThreadLocal<String> VERSION_CONTEXT = new TransmittableThreadLocal<>();

    public static void setVersion(String version) {
        VERSION_CONTEXT.set(version);
    }

    public static String getVersion() {
        return VERSION_CONTEXT.get();
    }

    public static void clear() {
        VERSION_CONTEXT.remove();
    }
}
