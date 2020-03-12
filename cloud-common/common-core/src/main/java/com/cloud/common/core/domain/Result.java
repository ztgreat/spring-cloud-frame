package com.cloud.common.core.domain;


import com.cloud.common.core.enums.CommonResultEnum;

/**
 * 返回结果类
 *
 * @author zhangteng
 */
public class Result<T> {

    /**
     * 0 成功，其余失败
     */
    private String code;

    private String msg;

    private T payload;


    public Result() {
        this.code = CommonResultEnum.SUCCESS.getCode();
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
     */
    public static Result build(String msg, String code, Object data) {
        return new Result<>(msg, code, data);
    }

    /**
     * 构建返回结果，code默认值为0
     */
    public static Result build(String msg, Object data) {
        return build(msg, CommonResultEnum.SUCCESS.getCode(), data);
    }

    /**
     * 构建成功结果
     */
    public static Result success(String msg, Object data) {
        return build(msg, CommonResultEnum.SUCCESS.getCode(), data);
    }

    /**
     * 构建成功结果带信息
     */
    public static Result success(String msg) {
        return success(msg, null);
    }

    /**
     * 构建成功结果待数据
     */
    public static Result success(Object data) {
        return success(null, data);
    }

    /**
     * 构建失败结果
     */
    public static Result fail(String code, String msg, Object data) {
        return build(msg, code, data);
    }

    /**
     * 构建普通失败结果
     */
    public static Result fail(String msg, Object data) {
        return build(msg, CommonResultEnum.FAIL.getCode(), data);
    }

    /**
     * 构建普通失败结果
     */
    public static Result fail(String msg) {
        return fail(CommonResultEnum.FAIL.getCode(), msg);
    }

    /**
     * 构建失败结果待数据
     */
    public static Result fail(String code, String msg) {
        return build(msg, code, null);
    }

    /**
     * 构建失败结果带数据
     */
    public static Result fail(Object data) {
        return fail(null, data);
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg=" + msg +
                ", payload=" + payload +
                "}";
    }
}
