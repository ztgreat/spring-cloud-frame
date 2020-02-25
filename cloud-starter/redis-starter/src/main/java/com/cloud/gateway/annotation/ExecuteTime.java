package com.cloud.gateway.annotation;

import java.lang.annotation.*;

/**
 * 描述: 记录调用时间
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExecuteTime {

    String value() default "";
}
