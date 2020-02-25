package com.cloud.delay.queue.redisson.listener;

import com.cloud.delay.queue.redisson.consts.ListenerType;
import com.cloud.delay.queue.redisson.handler.IsolationStrategy;
import com.cloud.delay.queue.redisson.handler.RedissonListenerErrorHandler;
import com.cloud.delay.queue.redisson.message.MessageConverter;
import lombok.Data;

@Data
public class ContainerProperties {

    private String queue;

    private ListenerType listenerType;

    private RedissonListenerErrorHandler errorHandler;

    private IsolationStrategy isolationStrategy;

    private MessageConverter messageConverter;

    private int concurrency;

    private int maxFetch;

}
