package com.cloud.common.domain;

import java.io.Serializable;

public class Payload<T> implements Serializable {
    private static final long serialVersionUID = -1549643581827130116L;
    private T payload;
    private String code = "0";
    private String msg = "ok";

    public Payload() {
    }
    public Payload(T payload) {
        this.payload = payload;
    }

    public Payload(T payload, String code, String msg) {
        this.payload = payload;
        this.code = code;
        this.msg = msg;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getPayload() {
        return this.payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}

