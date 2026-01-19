package com.minis.beans;

import com.minis.beans.factory.config.BeanDefinition;
import com.minis.beans.factory.support.AbstractAutowireCapableBeanFactory;
import com.minis.beans.factory.support.ConfigurableListableBeanFactory;
import com.minis.exception.BeansException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author huangyulu
 * @Date 2026/1/19 17:10
 * @Description IOC引擎类
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory
        implements ConfigurableListableBeanFactory {

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

    private List<String> beanDefinitionNames = new ArrayList<>();

    @Override
    public void registerDependentBean(String beanName, String dependentBeanName) {

    }

    @Override
    public String[] getDependentBeans(String beanName) {
        return new String[0];
    }

    @Override
    public String[] getDependenciesForBean(String beanName) {
        return new String[0];
    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public boolean containBeanDefinition(String beanName) {
        return false;
    }

    @Override
    public int getBeanDefinitionCount() {
        return this.beanDefinitionMap.size();
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return (String[]) this.beanDefinitionNames.toArray();
    }

    @Override
    public String[] getBeanNamesForType(Class<?> type) {
        List<String> result = new ArrayList<>();
        for (String beanName : this.beanDefinitionNames) {
            boolean matchFound = false;
            BeanDefinition beanDefinition = this.getBeanDefinition(beanName);
            Class<? extends BeanDefinition> aClass = beanDefinition.getClass();
            // 判断类型是否可以被赋值
            if (type.isAssignableFrom(aClass)) {
                matchFound = true;
            } else {
                matchFound = false;
            }

            if (matchFound) {
                result.add(beanName);
            }
        }

        return (String[]) result.toArray();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        String[] beanNames = getBeanNamesForType(type);
        Map<String, T> result = new LinkedHashMap<>(beanNames.length);
        for (String beanName : beanNames) {
            Object beanInstance = getBean(beanName);
            result.put(beanName, (T) beanInstance);
        }
        return result;
    }
}
