package com.cloud.eventbus.common.base;

/**
 * <p> the common check for other method </p>
 */
public class Preconditions {
    private Preconditions() {
    }

    public static <T extends Object> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }
}
