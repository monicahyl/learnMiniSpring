package com.minis.beans.argument;

/**
 * @Author huangyulu
 * @Date 2026/1/16 14:42
 * @Description setter注入
 */
public class PropertyValue {

    private final String type;
    private final String name;
    private final Object value;
    /*
    * 判断属性是引用类型还是普通的值类型
    * true: 是引用类型
    * false：普通值类型
    * */
    private final boolean isRef;

    public PropertyValue(String type, String name, Object value, boolean isRef) {
        this.type = type;
        this.name = name;
        this.value = value;
        this.isRef = isRef;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public boolean getIsRef() {
        return isRef;
    }

}
