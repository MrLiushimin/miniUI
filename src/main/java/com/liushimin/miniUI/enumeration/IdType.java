package com.liushimin.miniUI.enumeration;

import org.apache.commons.lang3.StringUtils;

public enum IdType implements BaseEnum {
    COMMON("0", "普通主键"),
    AUTO("1", "自增主键"),
    UUID("2", "UUID");

    private String code;

    private String text;

    IdType(String code, String text) {
        this.code = code;
        this.text = text;
    }
    public String getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

    public static String getTextByCode(String code) {
        String result = null;
        if (StringUtils.isEmpty(code)) {
            result = null;
        }
        for (BaseEnum value : IdType.values()) {
            if (code.equals(value.getCode())) {
                result = value.getText();
                break;
            }
        }
        return result;
    }

    /*@Override
    public String toString() {
        return "IdType{" +
                "code='" + code + '\'' +
                ", text='" + text + '\'' +
                '}';
    }*/
}
