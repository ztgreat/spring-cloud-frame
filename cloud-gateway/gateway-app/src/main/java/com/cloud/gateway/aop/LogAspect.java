package com.cloud.gateway.aop;

import com.alibaba.fastjson.JSON;
import com.cloud.gateway.extension.AppRuntimeEnv;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 日志统一打印切面
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    private static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    private final static String TENANT_KEY = "tenantId";

    private final static String TOKEN_KEY = "Authorization";

    @Resource
    private AppRuntimeEnv appRuntimeEnv;

    @Pointcut("execution (* com.cloud.gateway.controller..*.*(..))")
    public void apiLogAop() {
    }

    /**
     * 统一设置全局环境变量
     */
    @Before("apiLogAop()")
    public void setAppRuntimeEnv(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method == null) {
            return;
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        TenantId tenantAnno = method.getAnnotation(TenantId.class);

        //默认全局拦截，不包含注解，或者require=true，则拦截
        if (tenantAnno == null || tenantAnno.require()) {
            appRuntimeEnv.ensureTenantId(getParam(request, TENANT_KEY));
        } else {
            appRuntimeEnv.setTenantId(getParam(request, TENANT_KEY));
        }

        //token统一获取,eg:Authorization：Bearer xxxx
        Token tokeAnno = method.getAnnotation(Token.class);
        String auth = request.getHeader(TOKEN_KEY);
        if (tokeAnno == null || tokeAnno.require()) {
            appRuntimeEnv.ensureToken(StringUtil.isEmpty(auth) ? null : auth.split(" ")[1].trim());
        } else {
            appRuntimeEnv.setToken(StringUtil.isEmpty(auth) ? null : auth.split(" ")[1].trim());
        }
    }

    @Around("apiLogAop()")
    public Object aroundApi(ProceedingJoinPoint point) throws Throwable {

        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        LogAspectEnable logAspectEnable = method.getAnnotation(LogAspectEnable.class);
        // 方法前置日志打印
        if (logAspectEnable == null || logAspectEnable.start()) {
            logger.info("日志统一打印 ===== {}.{}() start =====,参数:\n{}", point.getSignature().getDeclaringTypeName(), point.getSignature().getName(), argsToString(point.getArgs()));
        }
        DateTime startTime = new DateTime();
        DateTime endTime;
        Interval interval;
        Object response;

        try {
            //执行该方法
            response = point.proceed();
        } catch (Exception e) {
            endTime = new DateTime();
            interval = new Interval(startTime, endTime);
            logger.info("日志统一打印 ===== {}.{}() end =====,响应时间:{}毫秒", point.getSignature().getDeclaringTypeName(), point.getSignature().getName(), interval.toDurationMillis());
            throw e;
        }

        if (logAspectEnable == null || logAspectEnable.end()) {
            if (logAspectEnable == null || logAspectEnable.timeOut()) {
                endTime = new DateTime();
                interval = new Interval(startTime, endTime);
                logger.info("日志统一打印 ===== {}.{}() end =====,响应时间:{}毫秒,响应内容:\n{}", point.getSignature().getDeclaringTypeName(), point.getSignature().getName(), interval.toDurationMillis(), argsToString(response));
            } else {
                logger.info("日志统一打印 ===== {}.{}() end =====,响应内容:\n{}", point.getSignature().getDeclaringTypeName(), point.getSignature().getName(), argsToString(response));
            }
        }
        return response;
    }

    private String argsToString(Object object) {
        try {
            return JSON.toJSONString(object);
        } catch (Exception e) {
            logger.warn("", e);
        }
        return String.valueOf(object);
    }

    /**
     * 获取业务参数
     *
     * @param request request
     * @param param   param
     * @throws Exception
     */
    private String getParam(HttpServletRequest request, String param) throws Exception {
        String[] reqParam = request.getParameterValues(param);
        return (reqParam == null || reqParam.length < 1 ? null : reqParam[0]);
    }
}
