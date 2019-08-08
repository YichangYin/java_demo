package com.constant;

/**
 * @program: java_demo
 * @description:
 * @author: Mr.Walloce
 * @create: 2019/06/18 15:30
 **/
public enum EnumConstants {

    MONDAY("MONDAY", "一"),
    TUESDAY("TUESDAY", "二"),
    WEDNESDAY("WEDNESDAY", "三"),
    THURSDAY("THURSDAY", "四"),
    FRIDAY("FRIDAY", "五"),
    SATURDAY("SATURDAY", "六"),
    SUNDAY("SUNDAY", "日");

    private final String value;
    private final String name;

    EnumConstants(String name, String value) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
