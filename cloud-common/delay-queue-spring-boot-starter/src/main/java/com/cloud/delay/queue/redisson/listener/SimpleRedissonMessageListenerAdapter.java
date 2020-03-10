package com.cloud.delay.queue.redisson.listener;


import com.cloud.delay.queue.redisson.exception.MessageConversionException;
import com.cloud.delay.queue.redisson.handler.RedissonListenerErrorHandler;
import com.cloud.delay.queue.redisson.message.MessageConverter;
import com.cloud.delay.queue.redisson.message.QueueMessage;
import com.cloud.delay.queue.redisson.message.RedissonMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.invocation.InvocableHandlerMethod;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.util.Assert;

import java.util.Map;

@Slf4j
public class SimpleRedissonMessageListenerAdapter extends AbstractRedissonMessageListenerAdapter<RedissonMessage> {

    private final InvocableHandlerMethod invocableHandlerMethod;

    private final RedissonListenerErrorHandler errorHandler;

    private MessagingMessageConverter messagingMessageConverter;

    public SimpleRedissonMessageListenerAdapter(InvocableHandlerMethod invocableHandlerMethod,
                                                MessageConverter messageConverter) {
        this(invocableHandlerMethod, messageConverter, null);
    }

    public SimpleRedissonMessageListenerAdapter(InvocableHandlerMethod invocableHandlerMethod,
                                                MessageConverter messageConverter,
                                                RedissonListenerErrorHandler errorHandler) {
        this.invocableHandlerMethod = invocableHandlerMethod;
        this.errorHandler = errorHandler;
        MessageConverter payloadConverter = messageConverter;
        if (payloadConverter == null) {
            payloadConverter = new SimpleMessageConverter();
        }
        this.messagingMessageConverter = new MessagingMessageConverter(payloadConverter);
    }

    @Override
    public void onMessage(RedissonMessage redissonMessage) throws Exception {
        Message message = this.messagingMessageConverter.fromMessage(redissonMessage);
        try {
            this.invocableHandlerMethod.invoke(message, redissonMessage);
        } catch (Exception e) {
            if (this.errorHandler != null) {
                this.errorHandler.handleError(redissonMessage, message, e);
            } else {
                throw e;
            }
        }
    }

    private static class MessagingMessageConverter implements MessageConverter {

        private final MessageConverter payloadConverter;

        private MessagingMessageConverter(MessageConverter payloadConverter) {
            Assert.notNull(payloadConverter, "payloadConverter must not be null");
            this.payloadConverter = payloadConverter;
        }

        @Override
        public QueueMessage<?> toMessage(Object payload, Map<String, Object> headers) throws MessageConversionException {
            return null;
        }

        @Override
        public Message fromMessage(RedissonMessage redissonMessage) throws MessageConversionException {
            Object convertedPayload = this.payloadConverter.fromMessage(redissonMessage);
            if (convertedPayload instanceof Message) {
                return (Message) convertedPayload;
            }
            return new GenericMessage<>(convertedPayload, redissonMessage.getHeaders());
        }
    }

}
