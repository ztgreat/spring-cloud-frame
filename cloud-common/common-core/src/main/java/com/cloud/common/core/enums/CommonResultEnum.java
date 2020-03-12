package com.cloud.common.core.enums;

/**
 * 公共结果枚举
 *
 * @author zhangteng
 */
public enum CommonResultEnum {

    SUCCESS("0", "成功"),
    FAIL("1000", "操作失败"),
    FLOW_LIMIT("2000", "限流"),
    TOKEN_NOT_FOUND("3000", "token不能为空"),
    TOKEN_IS_INVALID("4000", "token已失效"),


    //5000 以上 是业务编码


    ;

    private String code;

    private String msg;

    CommonResultEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
