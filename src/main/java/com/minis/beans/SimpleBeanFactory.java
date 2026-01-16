package com.minis.beans;

import com.minis.exception.BeansException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author huangyulu
 * @Date 2026/1/16 12:06
 * @Description
 */
public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry {

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

    private List<String> beanDefinitionNames = new ArrayList<>();

    public SimpleBeanFactory() {

    }


    /**
     * get bean,容器核心方法
     *
     * 1. 尝试获取Bean实例
     * 2. 获取不到，依据Bean定义来创建实例
     * 3. 注册Bean实例
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object getBean(String beanName) throws BeansException {
        // 尝试获取Bean实例
        Object singleton = this.getSingleton(beanName);
        if (singleton == null) {
            // 获取Bean定义来创建实例
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if (beanDefinition == null) {
                throw new BeansException("No bean with name '" + beanName + "' found");
            }
            try {
                singleton = Class.forName(beanDefinition.getClassName()).newInstance();
                // 注册Bean实例
                this.registerSingleton(beanName, singleton);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return singleton;
    }

    @Override
    public boolean containBean(String name) {
        return false;
    }

    @Override
    public boolean isSingleton(String name) {
        return this.beanDefinitionMap.get(name).isSingleton();
    }

    @Override
    public boolean isPrototype(String name) {
        return this.beanDefinitionMap.get(name).isPrototype();
    }

    @Override
    public Class<?> getType(String name) {
        return this.beanDefinitionMap.get(name).getClass();
    }


    @Override
    public void registerBean(String beanName, Object obj) {
        this.registerSingleton(beanName, obj);
    }

    @Override
    public Boolean containsBean(String name) {
        return containsSingleton(name);
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition bd) {
        this.beanDefinitionMap.put(name, bd);
        this.beanDefinitionNames.add(name);
        if (!bd.isLazyInit()) {
            // 容器启动时构建
            try {
                getBean(name);
            } catch (BeansException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeBeanDefinition(String name) {
        this.beanDefinitionMap.remove(name);
        this.beanDefinitionNames.remove(name);
        this.removeSingleton(name);
    }

    @Override
    public BeanDefinition getBeanDefinition(String name) {
        return this.beanDefinitionMap.get(name);
    }

    @Override
    public boolean containsBeanDefinition(String name) {
        return this.beanDefinitionMap.containsKey(name);
    }
}
