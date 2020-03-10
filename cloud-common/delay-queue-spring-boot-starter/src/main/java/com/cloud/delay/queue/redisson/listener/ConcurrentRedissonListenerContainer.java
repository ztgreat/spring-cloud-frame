package com.cloud.delay.queue.redisson.listener;

import com.cloud.delay.queue.redisson.consts.ListenerType;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.Lifecycle;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ConcurrentRedissonListenerContainer extends AbstractRedissonListenerContainer {

    @Getter
    private final int concurrency;

    private List<RedissonListenerContainer> containers = new ArrayList<>();


    private RedissonListenerContainerFactory containerFactory = new RedissonListenerContainerFactoryAdapter();

    public ConcurrentRedissonListenerContainer(ContainerProperties containerProperties, int concurrency) {
        super(containerProperties);
        Assert.isTrue(concurrency > 0, "concurrency must be greater than 0");
        this.concurrency = concurrency;
    }

    @Override
    protected void doStart() {
        for (int i = 0; i < this.concurrency; i++) {
            RedissonListenerContainer container = containerFactory.createListenerContainer(this.getContainerProperties());
            container.setRedissonClient(this.getRedissonClient());
            container.setListener(this.getRedissonListener());
            container.start();
            this.containers.add(container);
        }
    }

    @Override
    protected void doStop() {
        this.containers.forEach(Lifecycle::stop);
        this.containers.clear();
    }

    private static class RedissonListenerContainerFactoryAdapter implements RedissonListenerContainerFactory {
        @Override
        public RedissonListenerContainer createListenerContainer(ContainerProperties containerProperties) {
            ListenerType listenerType = containerProperties.getListenerType();
            if (listenerType == ListenerType.BATCH) {
                return new BatchRedissonListenerContainer(containerProperties, containerProperties.getMaxFetch());
            }
            return new SimpleRedissonListenerContainer(containerProperties);
        }
    }

}
