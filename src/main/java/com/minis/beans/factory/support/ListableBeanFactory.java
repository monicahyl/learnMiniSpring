package com.minis.beans.factory.support;

import com.minis.beans.factory.BeanFactory;
import com.minis.exception.BeansException;

import java.util.Map;

/**
 * @Author huangyulu
 * @Date 2026/1/19 16:17
 * @Description 将Bean作为集合来管理
 */
public interface ListableBeanFactory extends BeanFactory {

    /**
     * 根据Bean的名字判断是否包含Bean
     * @param beanName
     * @return
     */
    boolean containBeanDefinition(String beanName);

    /**
     * 获取Bean的数量
     * @return
     */
    int getBeanDefinitionCount();

    /**
     * 获取索引Bean的名字
     * @return
     */
    String[] getBeanDefinitionNames();

    /**
     * 根据某个类型获取Bean名字列表
     * @param type
     * @return
     */
    String[] getBeanNamesForType(Class<?> type);

    /**
     * 根据某个类型，获取Bean列表
     * @param type
     * @return
     * @param <T>
     * @throws BeansException
     */
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;
}
