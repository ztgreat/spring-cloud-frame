package com.cloud.delay.queue.redisson.listener;

public interface RedissonListenerContainerFactory {

    RedissonListenerContainer createListenerContainer(ContainerProperties containerProperties);

}
