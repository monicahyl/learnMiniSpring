package com.minis.test.service.impl;

import com.minis.test.service.AService;

public class AServiceImpl implements AService {

    // setter 注入
    private String property1;

//    构造器注入
    private String name;
    private int level;

    public AServiceImpl(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public AServiceImpl() {

    }

    public void setProperty1(String property1) {
        this.property1 = property1;
    }

    public void sayHello() {
        System.out.println("a service 1 say hello");
    }
}