package com.minis.core;

import com.minis.beans.factory.support.ConfigurableListableBeanFactory;
import com.minis.beans.processor.BeanFactoryPostProcessor;
import com.minis.core.env.Environment;
import com.minis.event.ApplicationEventPublisher;
import com.minis.exception.BeansException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Author huangyulu
 * @Date 2026/1/19 17:44
 * @Description
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

    private Environment environment;
    private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();
    private long startupDate;

    private final AtomicBoolean active = new AtomicBoolean();
    private final AtomicBoolean closed = new AtomicBoolean();
    private ApplicationEventPublisher applicationEventPublisher;


    /**
     * 获取Bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    public Object getBean(String beanName) throws BeansException {
        return getBeanFactory().getBean(beanName);
    }

    public List<BeanFactoryPostProcessor> getBeanFactoryPostProcessors() {
        return this.beanFactoryPostProcessors;
    }

    @Override
    public void refresh() throws BeansException, IllegalStateException {
        postProcessBeanFactory(getBeanFactory());
        registerBeanPostProcessors(getBeanFactory());
        initApplicationEventPublisher();
        onRefresh();
        registerListeners();
        finishRefresh();
    }


    // 注册监听者
    abstract void registerListeners();
    // 初始化事件发布者
    abstract void initApplicationEventPublisher();
    // 处理Bean以及对Bean的状态进行一些操作
    abstract void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory);
    abstract void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory);
    //  将初始化完毕的Bean进行应用上下文刷新以及完成刷新后进行自定义操作
    abstract void onRefresh();
    abstract void finishRefresh();

    @Override
    public String getApplicationName() {
        return "";
    }

    @Override
    public long getStartupDate() {
        return this.startupDate;
    }


    public abstract ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;

    @Override
    public void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor) {
        this.beanFactoryPostProcessors.add(postProcessor);
    }

    @Override
    public void close() {

    }

    @Override
    public boolean isActive() {
        return true;
    }
}
