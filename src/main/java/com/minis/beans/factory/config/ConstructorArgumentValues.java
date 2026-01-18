package com.minis.beans.factory.config;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author huangyulu
 * @Date 2026/1/17 11:02
 * @Description
 */
public class ConstructorArgumentValues {

    private final List<ConstructorArgumentValue> argumentValueList = new ArrayList<>();

    public ConstructorArgumentValues() {

    }

    public void addArgumentValue(ConstructorArgumentValue constructorArgumentValue) {
        argumentValueList.add(constructorArgumentValue);
    }

    public ConstructorArgumentValue getIndexedArgumentValue(int index) {
        ConstructorArgumentValue constructorArgumentValue = this.argumentValueList.get(index);
        return constructorArgumentValue;
    }

    public int getArgumentCount() {
        return this.argumentValueList.size();
    }

    public boolean isEmpty() {
        return this.argumentValueList.isEmpty();
    }


}
