package com.minis.context;

import com.minis.beans.BeanDefinition;
import com.minis.beans.BeanFactory;
import com.minis.beans.SimpleBeanFactory;
import com.minis.event.ApplicationEvent;
import com.minis.event.ApplicationEventPublisher;
import com.minis.exception.BeansException;

/**
 * @Author huangyulu
 * @Date 2026/1/16 11:04
 * @Description
 *
 * 1. 解析XML文件中的内容。
 * 2. 加载解析的内容，构建BeanDefinition。
 * 3. 读取BeanDefinition的配置信息，实例化Bean，然后把它注入到BeanFactory容器中
 */
public class ClassPathXmlApplicationContext implements BeanFactory, ApplicationEventPublisher {

    private SimpleBeanFactory beanFactory;

    /**
     * context负责整合容器的启动过程
     * 1. 读外部配置
     * 2. 解析Bean定义
     * 2. 创建BeanFactory
     * @param fileName
     */
    public ClassPathXmlApplicationContext(String fileName) {
       this(fileName, true);
    }

    public ClassPathXmlApplicationContext() {

    }

    public ClassPathXmlApplicationContext(String fileName, boolean isRefresh) {
        Resource resource = new ClassPathXmlResource(fileName);
        SimpleBeanFactory beanFactory = new SimpleBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);
        this.beanFactory = beanFactory;
        if (isRefresh) {
            this.beanFactory.refresh();
        }

    }

    /**
     * conext:对外提供一个getBean
     *
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object getBean(String beanName) throws BeansException {
        return this.beanFactory.getBean(beanName);
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
     * conext:对外提供一个registerBeanDefinition
     * @param beanDefinition
     */
    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanFactory.registerBeanDefinition(beanDefinition);
    }

    @Override
    public void registerBean(String beanName, Object obj) {
        this.beanFactory.registerBean(beanName, obj);
    }

    @Override
    public Boolean containsBean(String name) {
        return this.beanFactory.containsBean(name);
    }

    @Override
    public void publishEvent(ApplicationEvent event) {

    }

}
