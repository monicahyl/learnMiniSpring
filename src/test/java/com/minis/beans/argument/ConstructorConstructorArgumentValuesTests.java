package com.minis.beans.argument;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author huangyulu
 * @Date 2026/1/17 14:57
 * @Description
 */
public class ConstructorConstructorArgumentValuesTests {

    @Test
    public void test() {
//        System.out.println("hello world");

//        ConstructorArgumentValues argumentValues = new ConstructorArgumentValues();
//        ConstructorArgumentValue indexedArgumentValue = argumentValues.getIndexedArgumentValue(0);
//        System.out.println(indexedArgumentValue);

        List<String> refs = new ArrayList<>();
        refs.add("a");
        refs.add("b");
        refs.add("c");
        refs.add("d");

        Object[] array = refs.toArray();
        System.out.println(array.length);

        String[] array1 = refs.toArray(new String[0]);
        System.out.println(array1.length);
    }



}
