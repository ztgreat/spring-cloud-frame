package com.cloud.eventbus.common.base;

import java.util.concurrent.Executor;

enum DirectExecutor implements Executor {
    /** current executor **/
    INSTANCE;

    @Override
    public void execute(Runnable command) {
        command.run();
    }
}
