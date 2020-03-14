package com.cloud.ribbon.filter;

import cn.hutool.core.util.StrUtil;
import com.cloud.common.config.constant.CommonConstant;
import com.cloud.common.config.constant.ConfigConstants;
import com.cloud.common.config.context.FeignContextHolder;
import com.cloud.common.config.context.WebRequestContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * web request 过滤器
 * 这里传递 version 信息
 *
 * @author zhangteng
 */
@Slf4j
@ConditionalOnClass(Filter.class)
public class WebRequestFilter extends OncePerRequestFilter {

    /**
     * 是否开启自定义隔离规则
     */
    @Value("${" + ConfigConstants.CONFIG_RIBBON_ISOLATION_ENABLED + ":false}")
    private boolean enableIsolation;


    /**
     * 服务版本
     */
    @Value("${" + ConfigConstants.CONFIG_RIBBON_ISOLATION_VERSION + ":1.0.0}")
    private String serviceVersion;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !enableIsolation;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        try {

            // 设置默认版本
            WebRequestContextHolder.setVersion(serviceVersion);
            FeignContextHolder.setVersion(serviceVersion);

            // 设置指定版本
            String version = request.getHeader(CommonConstant.SERVICE_VERSION);
            if (StrUtil.isNotEmpty(version)) {
                WebRequestContextHolder.setVersion(version);
                FeignContextHolder.setVersion(version);
            }

            log.info("web request 请求版本:" + WebRequestContextHolder.getVersion() + ",请求地址:" + request.getRequestURI());

            filterChain.doFilter(request, response);
        } finally {
            FeignContextHolder.clear();
            WebRequestContextHolder.clear();
        }
    }
}
