package com.minis.test.service.impl;

import com.minis.beans.factory.annotation.Autowired;
import com.minis.test.service.AService;

public class AServiceImpl implements AService {

    // setter 注入
    private String property1;
    private String property2;

    //    构造器注入
    private String name;
    private int level;

    @Autowired
    private BaseServiceImpl baseservice;

//    public void setBaseService(BaseServiceImpl baseService) {
//        this.baseService = baseService;
//    }

//    public BaseServiceImpl getBaseService() {
//        return baseService;
//    }

    public AServiceImpl(String name, int level) {
        this.name = name;
        this.level = level;
        System.out.println(this.name + "," + this.level);
    }

    public AServiceImpl() {

    }

    public void setProperty1(String property1) {
        this.property1 = property1;
    }

    public void setProperty2(String property2) {
        this.property2 = property2;
    }

    public String getProperty1() {
        return property1;
    }

    public String getProperty2() {
        return property2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void sayHello(String name) {
        System.out.println(name + ": " + this.property2 + "," + this.property1);
//        System.out.println("hello world");
    }
}