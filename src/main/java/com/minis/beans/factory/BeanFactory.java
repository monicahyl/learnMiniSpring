package com.minis.beans.factory;

import com.minis.beans.factory.config.BeanDefinition;
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


    boolean containBean(String name);

    boolean isSingleton(String name);

    boolean isPrototype(String name);

    /**
     * 获取Bean的类型
     * @param name
     * @return
     */
    Class<?> getType(String name);

    /**
     * 注册Bean
     *
     * @param beanDefinition
     */
    @Deprecated
    default void registerBeanDefinition(BeanDefinition beanDefinition) {

    }


    /**
     * registerBeanDefinition -> registerBean
     * @param beanName
     * @param obj
     */
    void registerBean(String beanName, Object obj);

    Boolean containsBean(String name);






}
