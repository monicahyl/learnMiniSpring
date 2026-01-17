package com.minis.test.service.impl;

import com.minis.test.service.BaseService;
import com.minis.test.service.CaseService;

/**
 * @Author huangyulu
 * @Date 2026/1/17 16:38
 * @Description
 */
public class BaseServiceImpl implements BaseService {

    private CaseService caseService;

    public void setCaseService(CaseService caseService) {
        this.caseService = caseService;
    }
}
