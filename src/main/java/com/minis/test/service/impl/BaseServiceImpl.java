package com.minis.test.service.impl;

import com.minis.test.service.BaseService;

/**
 * @Author huangyulu
 * @Date 2026/1/17 16:38
 * @Description
 */
public class BaseServiceImpl implements BaseService {

    private CaseServiceImpl caseService;


    public void setCaseService(CaseServiceImpl caseService) {
        this.caseService = caseService;
    }

    @Override
    public void sayHello(String name) {
        caseService.sayHello(name);
    }
}
