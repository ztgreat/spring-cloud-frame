package com.cloud.member.extension;

import cn.hutool.core.util.IdUtil;
import org.springframework.stereotype.Component;

/**
 * 应用运行上下文
 * 遇到过很多这样的场景, 与领域无关的东西, 渗透到领域模型里面去
 * 想通过某种方式解决这个问题,
 */
@Component
public class AppRuntimeEnv {

    /**
     * 租户编码
     */
    private ThreadLocal<String> tenantId = ThreadLocal.withInitial(() -> null);
    /**
     * token信息
     */
    private ThreadLocal<String> token = ThreadLocal.withInitial(() -> null);

    private ThreadLocal<String> requestId = ThreadLocal.withInitial(IdUtil::simpleUUID);

    public void setTenantId(String tenantId) {
        this.tenantId.set(tenantId);
    }

    public void setToken(String token) {
        this.token.set(token);
    }

    public AppRuntimeEnv ensureToken(String token) {
        if (null == token) {
            throw new RuntimeException("token 不能为空");
        }
        this.token.set(token);
        return this;
    }

    public AppRuntimeEnv ensureTenantId(String tenantId) {
        if (null == tenantId) {
            throw new RuntimeException("tenantId 不能为空");
        }
        this.tenantId.set(tenantId);
        return this;
    }

    public String getTenantId() {
        return tenantId.get();
    }

    public String getToken() {
        return token.get();
    }

    public String getRequestId() {
        return requestId.get();
    }

}