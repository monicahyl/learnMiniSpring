package com.minis;

import com.minis.beans.factory.BeanFactory;
import com.minis.beans.factory.config.SingletonBeanRegistry;
import com.minis.beans.processor.BeanPostProcessor;

/**
 * @Author huangyulu
 * @Date 2026/1/19 16:23
 * @Description
 * 1. 维护Bean之间的依赖关系
 * 2. 支持Bean处理器
 */
public interface ConfigurableBeanFactory extends BeanFactory, SingletonBeanRegistry {

    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    int getBeanPostProcessorCount();

    void registerDependentBean(String beanName, String dependentBeanName);

    String[] getDependentBeans(String beanName);

    String[] getDependenciesForBean(String beanName);

}
