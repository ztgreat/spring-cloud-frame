package com.cloud.order.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAspectEnable {
    //是否打印 方法日志开始
    boolean start() default true;

    // 是否打印 方法结束 日志
    boolean end() default true;

    // 是否进行 超时控制
    boolean timeOut() default true;
}
