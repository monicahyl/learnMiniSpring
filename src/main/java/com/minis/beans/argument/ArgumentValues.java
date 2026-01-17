package com.minis.beans.argument;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author huangyulu
 * @Date 2026/1/17 11:02
 * @Description
 */
public class ArgumentValues {

    private final List<ArgumentValue> argumentValueList = new ArrayList<>();

    public ArgumentValues() {

    }

    public void addArgumentValue(ArgumentValue argumentValue) {
        argumentValueList.add(argumentValue);
    }

    public ArgumentValue getIndexedArgumentValue(int index) {
        ArgumentValue argumentValue = this.argumentValueList.get(index);
        return argumentValue;
    }

    public int getArgumentCount() {
        return this.argumentValueList.size();
    }

    public boolean isEmpty() {
        return this.argumentValueList.isEmpty();
    }


}
