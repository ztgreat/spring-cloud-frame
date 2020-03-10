package com.cloud.delay.queue.redisson.handler;

import com.cloud.delay.queue.redisson.message.RedissonMessage;
import org.springframework.messaging.Message;

@FunctionalInterface
public interface RedissonListenerErrorHandler {

    /**
     * error handler
     *
     * @param message   redisson message
     * @param throwable throwable
     */
    void handleError(RedissonMessage message, Message<?> messagingMessage, Throwable throwable);

}
