package com.cloud.delay.queue.redisson.annotation;

import com.cloud.delay.queue.redisson.config.EnableRedissonConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({EnableRedissonConfiguration.class})
public @interface EnableRedisson {

}
