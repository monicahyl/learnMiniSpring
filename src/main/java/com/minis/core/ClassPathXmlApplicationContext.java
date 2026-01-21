package com.minis.core;

import com.minis.beans.DefaultListableBeanFactory;
import com.minis.beans.factory.support.ConfigurableListableBeanFactory;
import com.minis.beans.factory.xml.XmlBeanDefinitionReader;
import com.minis.beans.processor.AutowiredAnnotationBeanPostProcessor;
import com.minis.beans.processor.BeanFactoryPostProcessor;
import com.minis.beans.processor.BeanPostProcessor;
import com.minis.context.ClassPathXmlResource;
import com.minis.context.Resource;
import com.minis.core.env.Environment;
import com.minis.event.*;
import com.minis.exception.BeansException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Author huangyulu
 * @Date 2026/1/19 17:57
 * @Description
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

    DefaultListableBeanFactory beanFactory;

    private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();

    ApplicationEventPublisher applicationEventPublisher;

//    List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    public ClassPathXmlApplicationContext(String fileName) {
        this(fileName, true);
    }

    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public ApplicationEventPublisher getApplicationEventPublisher() {
        return applicationEventPublisher;
    }

    public ClassPathXmlApplicationContext(String fileName, boolean isRefresh) {
        Resource resource = new ClassPathXmlResource(fileName);
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);
        this.beanFactory = beanFactory;
        if (isRefresh) {
            try {
                refresh();
            } catch (BeansException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    void registerListeners() {
        ApplicationListener listener = new ApplicationListener();
        this.getApplicationEventPublisher().addApplicationListener(listener);
    }

    @Override
    void initApplicationEventPublisher() {
        ApplicationEventPublisher sep = new SimpleApplicationEventPublisher();
        this.setApplicationEventPublisher(sep);
    }



    @Override
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {

    }

    @Override
    void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        this.beanFactory.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());
    }

    @Override
    void onRefresh() {
        this.beanFactory.refresh();
    }

    @Override
    void finishRefresh() {
        publishEvent(new ContextRefreshEvent("Context Refreshed..."));
    }


    @Override
    public void setEnvironment(Environment environment) {

    }

    @Override
    public Environment getEnvironment() {
        return null;
    }

    @Override
    public void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor) {
        this.beanFactoryPostProcessors.add(postProcessor);
    }

    @Override
    public ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException {
        return this.beanFactory;
    }


    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {

    }

    @Override
    public int getBeanPostProcessorCount() {
        return 0;
    }

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
    public void registerSingleton(String beanName, Object singletonObject) {

    }

    @Override
    public Object getSingleton(String beanName) {
        return null;
    }

    @Override
    public boolean containsSingleton(String beanName) {
        return false;
    }

    @Override
    public String[] getSingletonNames() {
        return new String[0];
    }

    @Override
    public boolean containBeanDefinition(String beanName) {
        return false;
    }

    @Override
    public int getBeanDefinitionCount() {
        return 0;
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return new String[0];
    }

    @Override
    public String[] getBeanNamesForType(Class<?> type) {
        return new String[0];
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return Collections.emptyMap();
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

    @Override
    public void registerBean(String beanName, Object obj) {

    }

    @Override
    public Boolean containsBean(String name) {
        return null;
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        this.getApplicationEventPublisher().publishEvent(event);
    }

    @Override
    public void addApplicationListener(ApplicationListener listener) {
        this.getApplicationEventPublisher().addApplicationListener(listener);
    }


}
