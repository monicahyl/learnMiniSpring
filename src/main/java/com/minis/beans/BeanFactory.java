package com.minis.beans;

import com.minis.BeanDefinition;
import com.minis.exception.BeansException;

/**
 * @Author huangyulu
 * @Date 2026/1/16 10:35
 * @Description 工厂模式
 */
public interface BeanFactory {

    /**
     * 基于beanName获取Bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object getBean(String beanName) throws BeansException;

    /**
     * 注册Bean
     * @param beanDefinition
     */
    void registerBeanDefinition(BeanDefinition beanDefinition);
}
