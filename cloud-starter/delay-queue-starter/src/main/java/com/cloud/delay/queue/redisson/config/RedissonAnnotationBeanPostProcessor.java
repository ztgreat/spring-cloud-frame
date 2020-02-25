package com.cloud.delay.queue.redisson.config;

import com.cloud.delay.queue.redisson.annotation.RedissonListener;
import com.cloud.delay.queue.redisson.consts.ListenerType;
import com.cloud.delay.queue.redisson.handler.IsolationStrategy;
import com.cloud.delay.queue.redisson.handler.RedissonListenerErrorHandler;
import com.cloud.delay.queue.redisson.listener.*;
import com.delay.queue.redisson.listener.*;
import com.cloud.delay.queue.redisson.message.MessageConverter;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionValidationException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;
import org.springframework.messaging.handler.invocation.InvocableHandlerMethod;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class RedissonAnnotationBeanPostProcessor implements BeanPostProcessor, BeanFactoryAware, SmartInitializingSingleton {

    private BeanFactory beanFactory;

    private MessageHandlerMethodFactory messageHandlerMethodFactory;

    private RedissonListenerContainerFactory redissonListenerContainerFactory;

    private RedissonListenerRegistry redissonListenerRegistry;

    private RedissonClient redissonClient;

    private List<ListenerDescriptor> listenerDescriptors = new ArrayList<>(8);

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
        DefaultMessageHandlerMethodFactory handlerMethodFactory = new DefaultMessageHandlerMethodFactory();
        handlerMethodFactory.setBeanFactory(beanFactory);
        handlerMethodFactory.afterPropertiesSet();
        this.messageHandlerMethodFactory = handlerMethodFactory;
        this.redissonListenerContainerFactory = new DefaultRedissonListenerContainerFactory();
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        List<Method> allMethod = findAllMethod(clazz);
        allMethod.forEach(method -> processMethod(bean, method));
        return bean;
    }

    private List<Method> findAllMethod(Class clazz) {
        final List<Method> methods = new LinkedList<>();
        ReflectionUtils.doWithMethods(clazz, methods::add);
        return methods;
    }

    private void processMethod(final Object bean, final Method method) {
        RedissonListener annotation = AnnotationUtils.findAnnotation(method, RedissonListener.class);
        if (annotation == null) {
            return;
        }
        final int maxFetch = annotation.maxFetch();
        if (maxFetch <= 0) {
            throw new BeanDefinitionValidationException("maxFetch must be grater than 0");
        }
        String[] queues = annotation.queues();
        for (String queue : queues) {
            InvocableHandlerMethod invocableHandlerMethod = this.messageHandlerMethodFactory.createInvocableHandlerMethod(bean, method);
            ListenerDescriptor listenerDescriptor = new ListenerDescriptor();
            listenerDescriptor.setQueueInterested(queue);
            listenerDescriptor.setListenerType(maxFetch > 1 ? ListenerType.BATCH : ListenerType.SIMPLE);
            listenerDescriptor.setHandlerMethod(invocableHandlerMethod);
            listenerDescriptor.setErrorHandlerName(annotation.errorHandler());
            listenerDescriptor.setIsolationStrategyName(annotation.isolationStrategy());
            listenerDescriptor.setMessageConverterName(annotation.messageConverter());
            listenerDescriptor.setConcurrency(annotation.concurrency());
            listenerDescriptor.setMaxFetch(maxFetch);
            this.listenerDescriptors.add(listenerDescriptor);
        }
    }

    @Data
    private static class ListenerDescriptor {

        private String queueInterested;

        private ListenerType listenerType;

        private InvocableHandlerMethod handlerMethod;

        private String errorHandlerName;

        private String isolationStrategyName;

        private String messageConverterName;

        private int concurrency;

        private int maxFetch;

    }

    @Override
    public void afterSingletonsInstantiated() {
        this.redissonClient = this.beanFactory.getBean(RedissonClient.class);
        this.redissonListenerRegistry = this.beanFactory.getBean(RedissonConfigUtils.REDISSON_LISTENER_REGISTRY_BEAN_NAME, RedissonListenerRegistry.class);
        this.listenerDescriptors.forEach(descriptor -> {
            final String queueInterested = descriptor.getQueueInterested();
            InvocableHandlerMethod invocableHandlerMethod = descriptor.getHandlerMethod();
            RedissonListenerErrorHandler errorHandler = null;
            IsolationStrategy isolationStrategy = null;
            final String errorHandlerName = descriptor.getErrorHandlerName();
            final String isolationStrategyName = descriptor.getIsolationStrategyName();
            final String messageConverterName = descriptor.getMessageConverterName();
            if (StringUtils.hasText(errorHandlerName)) {
                errorHandler = this.beanFactory.getBean(errorHandlerName, RedissonListenerErrorHandler.class);
            }
            final ListenerType listenerType = descriptor.getListenerType();
            if (StringUtils.hasText(isolationStrategyName)) {
                isolationStrategy = this.beanFactory.getBean(isolationStrategyName, IsolationStrategy.class);
            }
            MessageConverter messageConverter = null;
            if (StringUtils.hasText(messageConverterName)) {
                messageConverter = this.beanFactory.getBean(messageConverterName, MessageConverter.class);
            } else {
                try {
                    messageConverter = this.beanFactory.getBean(MessageConverter.class);
                } catch (BeansException e) {
                    log.trace("no MessageConverter found for RedissonMessageListener to apply");
                }
            }
            final MessageConverter payloadMessageConverter = messageConverter;
            final String isolatedQueueName = isolationStrategy == null ? queueInterested : isolationStrategy.getRedisQueueName(queueInterested);
            ContainerProperties containerProperties = new ContainerProperties();
            containerProperties.setQueue(isolatedQueueName);
            containerProperties.setListenerType(listenerType);
            containerProperties.setErrorHandler(errorHandler);
            containerProperties.setIsolationStrategy(isolationStrategy);
            containerProperties.setMessageConverter(payloadMessageConverter);
            containerProperties.setConcurrency(descriptor.getConcurrency());
            containerProperties.setMaxFetch(descriptor.getMaxFetch());

            RedissonMessageListener messageListener = this.createMessageListener(containerProperties, invocableHandlerMethod);
            RedissonListenerContainer listenerContainer = this.redissonListenerContainerFactory.createListenerContainer(containerProperties);

            listenerContainer.setRedissonClient(this.redissonClient);
            listenerContainer.setListener(messageListener);
            this.redissonListenerRegistry.registerListenerContainer(listenerContainer);
        });
    }

    private RedissonMessageListener createMessageListener(ContainerProperties containerProperties, InvocableHandlerMethod invocableHandlerMethod) {
        if (containerProperties.getListenerType() == ListenerType.BATCH) {
            return new BatchRedissonMessageListenerAdapter(invocableHandlerMethod, containerProperties.getMessageConverter(), containerProperties.getErrorHandler());
        }
        return new SimpleRedissonMessageListenerAdapter(invocableHandlerMethod, containerProperties.getMessageConverter(), containerProperties.getErrorHandler());
    }

}
