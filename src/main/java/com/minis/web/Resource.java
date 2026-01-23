package com.minis.web;

/**
 * @Author huangyulu
 * @Date 2026/1/21 17:19
 * @Description
 */
public interface Resource extends Iterable<Object> {

    boolean hasNext();

    Object next();
}
