package com.minis.beans;

import com.minis.beans.factory.BeanFactory;
import com.minis.beans.factory.config.BeanDefinition;
import com.minis.exception.BeansException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author huangyulu
 * @Date 2026/1/16 10:53
 * @Description
 */
@Deprecated
public class SimpleBeanFactoryV1 implements BeanFactory {

    private List<BeanDefinition> beanDefinitions = new ArrayList<>();
    private List<String> beanNames = new ArrayList<>();
    private Map<String, Object> singletons = new HashMap<>();


    public SimpleBeanFactoryV1() {

    }

    /**
     * getBean, 容器核心方法
     * 先尝试获取Bean，Bean为null就获取定义来创建Bean
     *
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object getBean(String beanName) throws BeansException {
        Object singleton = singletons.get(beanName);
        if (singleton == null) {
            int index = beanNames.indexOf(beanName);
            if (index == -1) {
                throw new BeansException("Bean not found: " + beanName);
            } else {
                // 获取Bean定义并初始化Bean
                BeanDefinition beanDefinition = beanDefinitions.get(index);
                try {
                    singleton = Class.forName(beanDefinition.getClassName()).newInstance();
                    singletons.put(beanDefinition.getId(), singleton);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
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
        return false;
    }

    @Override
    public boolean isPrototype(String name) {
        return false;
    }

    @Override
    public Class<?> getType(String name) {
        return null;
    }

    /**
     * 把beans.xml配置文件里面的所有注册的Bean信息都加载进来
     *
     * @param beanDefinition
     */
    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanDefinitions.add(beanDefinition);
        this.beanNames.add(beanDefinition.getId());
    }

    @Override
    public void registerBean(String beanName, Object obj) {

    }

    @Override
    public Boolean containsBean(String name) {
        return null;
    }
}
