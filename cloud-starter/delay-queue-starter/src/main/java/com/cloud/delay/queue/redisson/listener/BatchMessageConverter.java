package com.cloud.delay.queue.redisson.listener;

import com.cloud.delay.queue.redisson.exception.MessageConversionException;
import com.cloud.delay.queue.redisson.message.MessageConverter;
import com.cloud.delay.queue.redisson.message.QueueMessage;
import com.cloud.delay.queue.redisson.message.RedissonMessage;
import org.springframework.messaging.Message;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface BatchMessageConverter extends MessageConverter {

    @Override
    default QueueMessage<?> toMessage(Object payload, Map<String, Object> headers) throws MessageConversionException {
        throw new UnsupportedOperationException("please see [toListMessage] method");
    }

    @Override
    default Object fromMessage(RedissonMessage redissonMessage) throws MessageConversionException {
        return this.fromMessage(Collections.singletonList(redissonMessage));
    }

    List<QueueMessage<?>> toListMessage(Object payload, Map<String, Object> headers);

    Message<?> fromMessage(List<RedissonMessage> redissonMessages) throws MessageConversionException;

}
