package com.minis.beans;

/**
 * @Author huangyulu
 * @Date 2026/1/16 11:41
 * @Description 管理单例Bean
 */
public interface SingletonBeanRegistry {

    void registerSingleton(String beanName, Object singletonObject);

    Object getSingleton(String beanName);

    boolean containsSingleton(String beanName);

    String[] getSingletonNames();
}
