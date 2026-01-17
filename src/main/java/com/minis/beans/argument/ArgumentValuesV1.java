//package com.minis.beans.argument;
//
//import java.util.*;
//
///**
// * @Author huangyulu
// * @Date 2026/1/16 14:47
// * @Description 构造器注入
// */
//public class ArgumentValuesV1 {
//
//    private final Map<Integer, ArgumentValue> indexedArgumentValues = new HashMap<>(0);
//
//    private final List<ArgumentValue> genericArgumentValues = new LinkedList<>();
//
//    public ArgumentValuesV1() {
//
//    }
//
//    public void addArgumentValue(Integer key, ArgumentValue newValue) {
//        this.indexedArgumentValues.put(key, newValue);
//    }
//
//    public boolean hasIndexedArgumentValue(int index) {
//        return this.indexedArgumentValues.containsKey(index);
//    }
//
//    public ArgumentValue getArgumentValue(int index) {
//        return this.indexedArgumentValues.get(index);
//    }
//
//
//    public void addGenericArgumentValue(ArgumentValue newValue) {
//        if (newValue.getName() != null) {
//            for (Iterator<ArgumentValue> it = this.genericArgumentValues.iterator();
//                 it.hasNext();
//            ) {
//                ArgumentValue currentValue = it.next();
//                if (newValue.getName().equals(currentValue.getName())) {
//                    it.remove();
//                }
//            }
//        }
//
//        this.genericArgumentValues.add(newValue);
//    }
//
//    public ArgumentValue getGenericArgumentValue(String requiredName) {
//        for (ArgumentValue valueHolder : this.genericArgumentValues) {
//            if (valueHolder.getValue() != null
//                    && (requiredName == null || !valueHolder.getName().equals(requiredName))) {
//                continue;
//            }
//            return valueHolder;
//        }
//        return null;
//    }
//
//    public int getArgumentValueCount() {
//        return this.genericArgumentValues.size();
//    }
//
//    public boolean isEmpty() {
//        return this.genericArgumentValues.isEmpty();
//    }
//
//
//}
