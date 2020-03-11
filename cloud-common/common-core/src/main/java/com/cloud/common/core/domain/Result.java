package com.cloud.common.core.domain;


/**
 * 返回结果类
 */
public class Result<T> {


    private String msg = "操作成功";

    /**
     * 0 成功，其余失败
     */
    private String code = "0";

    private T payload;


    public Result() {
    }

    private Result(String msg, String code, T data) {
        this.msg = msg;
        this.code = code;
        this.payload = data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public String getCode() {
        return code;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    /**
     * 构建返回结果
     *
     * @param msg
     * @param code
     * @param data
     * @return
     */
    public static Result build(String msg, String code, Object data) {
        return new Result(msg, code, data);
    }

    /**
     * 构建返回结果，code默认值为0
     *
     * @param msg
     * @param data
     * @return
     */
    public static Result build(String msg, Object data) {
        return build(msg, "0", data);
    }

    /**
     * 构建成功结果
     *
     * @param msg
     * @param data
     * @return
     */
    public static Result buildSuccess(String msg, Object data) {
        return build(msg, "0", data);
    }

    /**
     * 构建失败结果
     *
     * @param msg
     * @param data
     * @return
     */
    public static Result buildFailure(String code, String msg, Object data) {
        return build(msg, code, data);
    }

    /**
     * 构建失败结果
     *
     * @param msg
     * @param data
     * @return
     */
    public static Result buildFailure(String msg, Object data) {
        return build(msg, "500", data);
    }

    /**
     * 构建成功结果带信息
     *
     * @param msg
     * @return
     */
    public static Result buildSuccess(String msg) {
        return buildSuccess(msg, null);
    }

    /**
     * 构建成功结果待数据
     *
     * @param data
     * @return
     */
    public static Result buildSuccess(Object data) {
        return buildSuccess(null, data);
    }

    /**
     * 构建失败结果待数据
     *
     * @param msg
     * @return
     */
    public static Result buildFailure(String msg) {
        return buildFailure(msg, null);
    }

    /**
     * 构建失败结果待数据
     *
     * @param code
     * @param msg
     * @return
     */
    public static Result buildFailure(String code, String msg) {
        return build(msg, code, null);
    }

    /**
     * 构建失败结果带数据
     *
     * @param data
     * @return
     */
    public static Result buildFailure(Object data) {
        return buildFailure("", data);
    }
}
