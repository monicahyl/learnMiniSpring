package com.minis.test.controller;

import com.minis.web.anno.RequestMapping;

/**
 * @Author huangyulu
 * @Date 2026/1/21 17:09
 * @Description
 */
public class TestController {


    @RequestMapping("/test")
    public String doGet() {
        System.out.println("TestController.doGet()");
        return "hello world!";
    }

    public String doPost() {
        return "hello world!";
    }

}
