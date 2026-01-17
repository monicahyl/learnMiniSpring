package com.minis.test.service.impl;

import com.minis.test.service.AService;
import com.minis.test.service.CaseService;

/**
 * @Author huangyulu
 * @Date 2026/1/17 16:38
 * @Description
 */
public class CaseServiceImpl implements CaseService {
    private AService aService;

    public void setAService(AService aService) {
        this.aService = aService;
    }
}
