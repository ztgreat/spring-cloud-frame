package com.cloud.eventbus.common.base;

import cn.hutool.core.thread.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * <p> executor factory </p>
 */
public class MoreExecutors {

    /**
     * current thread as the executor
     **/
    public static Executor directExecutor() {
        return DirectExecutor.INSTANCE;
    }

    /**
     * a new thread as the executor
     **/
    public static Executor oneThreadExecutor(String identifier) {
        ThreadFactory threadFactory = ThreadFactoryBuilder.create().setNamePrefix(identifier).build();
        return new ThreadPoolExecutor(1, 1, 60L, TimeUnit.SECONDS
                , new LinkedBlockingQueue<>(65535), threadFactory);
    }
}
