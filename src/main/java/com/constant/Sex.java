package com.constant;

public enum Sex {

    MAN("1","男"),FEMAN("2","女");

    private Sex(String value,String name){
        this.value = value;
        this.name = name;
    }
    private final String value;
    private final String name;

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
