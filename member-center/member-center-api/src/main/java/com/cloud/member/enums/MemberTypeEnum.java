package com.cloud.member.enums;

import cn.hutool.core.util.StrUtil;

public enum MemberTypeEnum {
    /**
     * 会员
     */
    MEMBER("member", "会员"),
    /**
     * 游客
     */
    VISITOR("visitor", "游客");

    MemberTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    private String code;
    private String value;

    /**
     * 根据code获取value
     *
     * @param code
     * @return
     */
    public static String getValueByCode(String code) {
        if (StrUtil.isBlank(code)) {
            return null;
        }
        for (MemberTypeEnum memberTypeEnum : MemberTypeEnum.values()) {
            if (memberTypeEnum.getCode().equals(code)) {
                return memberTypeEnum.getValue();
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

}
