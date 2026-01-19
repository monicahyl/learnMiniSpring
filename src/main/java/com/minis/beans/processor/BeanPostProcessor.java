package com.minis.beans.processor;

import com.minis.beans.factory.BeanFactory;
import com.minis.exception.BeansException;

/**
 * @Author huangyulu
 * @Date 2026/1/18 20:42
 * @Description
 */
public interface BeanPostProcessor {

    /**
     * bean初始化之前
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * bean初始化之后
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;


    void setBeanFactory(BeanFactory beanFactory);

}
