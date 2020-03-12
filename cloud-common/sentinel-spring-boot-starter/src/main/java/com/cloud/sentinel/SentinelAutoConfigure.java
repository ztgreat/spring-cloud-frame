package com.cloud.sentinel;

import com.alibaba.csp.sentinel.adapter.servlet.callback.WebCallbackManager;
import com.cloud.sentinel.handler.CustomUrlBlockHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Sentinel配置类
 */
@Slf4j
public class SentinelAutoConfigure {
    public SentinelAutoConfigure() {

        log.info("============ Sentinel配置加载");
        WebCallbackManager.setUrlBlockHandler(new CustomUrlBlockHandler());
    }


}