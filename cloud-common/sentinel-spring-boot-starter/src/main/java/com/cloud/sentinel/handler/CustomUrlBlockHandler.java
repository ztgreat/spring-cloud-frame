package com.cloud.sentinel.handler;

import cn.hutool.json.JSONUtil;
import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlBlockHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.cloud.common.core.domain.Result;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 限流、熔断统一处理类
 */
@Slf4j
public class CustomUrlBlockHandler implements UrlBlockHandler {
    @Override
    public void blocked(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws IOException {
        Result result = Result.fail("flow-limiting");

        log.info("============:{}", result);
        httpServletResponse.getWriter().print(JSONUtil.toJsonStr(result));
    }
}
