package com.minis.test.service.impl;

import com.minis.test.service.CaseService;

/**
 * @Author huangyulu
 * @Date 2026/1/17 16:38
 * @Description
 */
public class CaseServiceImpl implements CaseService {
    private AServiceImpl aService;

    public void setAService(AServiceImpl aService) {
        this.aService = aService;
    }

    @Override
    public void sayHello(String name) {
        aService.sayHello(name);
    }
}
