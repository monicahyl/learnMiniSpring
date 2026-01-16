package com.minis.beans.argument;

/**
 * @Author huangyulu
 * @Date 2026/1/16 14:41
 * @Description 构造器注入
 */
public class ArgumentValue {

//  参数名、参数值、参数类型
    private Object value;
    private String type;
    public String name;

    public ArgumentValue(Object value, String type, String name) {
        this.value = value;
        this.type = type;
        this.name = name;
    }

    public ArgumentValue(Object value, String type) {
        this.value = value;
        this.type = type;
    }

    public ArgumentValue() {

    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
