package com.cloud.ribbon.config;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.cloud.common.config.constant.CommonConstant;
import com.cloud.common.config.constant.SecurityConstants;
import com.cloud.common.config.context.FeignContextHolder;
import com.cloud.common.config.context.WebRequestContextHolder;
import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

/**
 * feign拦截器，只包含http相关数据
 */
@Slf4j
public class FeignHttpInterceptorConfig {

    private List<String> requestHeaders = new ArrayList<>();

    @PostConstruct
    public void initialize() {
        requestHeaders.add(CommonConstant.TOKEN_HEADER);
        requestHeaders.add(SecurityConstants.USER_ID_HEADER);
        requestHeaders.add(SecurityConstants.USER_HEADER);
        requestHeaders.add(CommonConstant.SERVICE_VERSION);
    }

    /**
     * 使用feign client访问别的微服务时，将上游传过来的token等信息放入header传递给下一个服务
     */
    @Bean
    public RequestInterceptor httpFeignInterceptor() {
        return template -> {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes();

            List<String> serviceVersions = (List<String>) template.headers().get(CommonConstant.SERVICE_VERSION);

            //默认 设置web 请求版本
            FeignContextHolder.setVersion(WebRequestContextHolder.getVersion());

            //设置 用户指定版本
            if (CollectionUtil.isNotEmpty(serviceVersions)) {
                FeignContextHolder.setVersion(serviceVersions.get(0));
            }

            log.info("feign 请求版本:" + FeignContextHolder.getVersion() + ",请求路径:" + template.request().url());

            if (Objects.isNull(attributes)) {
                return;
            }
            HttpServletRequest request = attributes.getRequest();
            Enumeration<String> headerNames = request.getHeaderNames();
            if (CollectionUtil.isEmpty(headerNames)) {
                return;
            }

            String headerName;
            String headerValue;
            while (headerNames.hasMoreElements()) {
                headerName = headerNames.nextElement();
                if (requestHeaders.contains(headerName)) {
                    headerValue = request.getHeader(headerName);
                    template.header(headerName, headerValue);
                }
            }

            //传递token，无网络隔离时需要传递
            String token = extractHeaderToken(request);
            if (StrUtil.isEmpty(token)) {
                token = request.getParameter(CommonConstant.TOKEN_HEADER);
            }
            if (StrUtil.isNotEmpty(token)) {
                template.header(CommonConstant.TOKEN_HEADER, CommonConstant.BEARER_TYPE + " " + token);
            }
        };
    }

    /**
     * 解析head中的token
     *
     * @param request
     */
    private String extractHeaderToken(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders(CommonConstant.TOKEN_HEADER);
        while (headers.hasMoreElements()) {
            String value = headers.nextElement();
            if ((value.toLowerCase().startsWith(CommonConstant.BEARER_TYPE))) {
                String authHeaderValue = value.substring(CommonConstant.BEARER_TYPE.length()).trim();
                int commaIndex = authHeaderValue.indexOf(',');
                if (commaIndex > 0) {
                    authHeaderValue = authHeaderValue.substring(0, commaIndex);
                }
                return authHeaderValue;
            }
        }
        return null;
    }
}
