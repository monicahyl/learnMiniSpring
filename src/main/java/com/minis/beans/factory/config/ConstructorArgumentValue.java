package com.minis.beans.factory.config;

/**
 * @Author huangyulu
 * @Date 2026/1/16 14:41
 * @Description 构造器注入
 */
public class ConstructorArgumentValue {

    //  参数名、参数值、参数类型
    private String type;
    public String name;
    private Object value;

    public ConstructorArgumentValue(String type, String name, Object value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public ConstructorArgumentValue(String type, Object value) {
        this.type = type;
        this.value = value;
    }

    public ConstructorArgumentValue() {

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
