package com.cloud.delay.queue.redisson.message;

import com.cloud.delay.queue.redisson.exception.MessageConversionException;

import java.util.Map;
import java.util.UUID;

public class DefaultRedissonMessageConverter implements MessageConverter {

    @Override
    public QueueMessage<?> toMessage(Object payload, Map<String, Object> headers) {
        headers.put(RedissonHeaders.MESSAGE_ID, UUID.randomUUID().toString());
        return QueueMessageBuilder.withPayload(payload).headers(headers).build();
    }

    @Override
    public Object fromMessage(RedissonMessage redissonMessage) throws MessageConversionException {
        return new String(redissonMessage.getPayload());
    }

}
