package com.cloud.eventbus.common.constant;

import com.cloud.eventbus.common.EventBus;
import com.cloud.eventbus.common.annotation.Subscribe;

/**
 * <p> the priority in method register in {@link EventBus}
 * , this class is used into the field priority in {@link Subscribe} </p>
 */
public class Priority {
    private Priority() {
    }

    public static final int S_LEVEL = 0;

    public static final int M_LEVEL = 100;

    public static final int L_LEVEL = 200;

    public static final int XL_LEVEL = 300;

    public static final int XXL_LEVEL = 400;
}
