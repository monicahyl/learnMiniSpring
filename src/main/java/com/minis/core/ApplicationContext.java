package com.minis.core;

import com.minis.ConfigurableBeanFactory;
import com.minis.beans.factory.support.ConfigurableListableBeanFactory;
import com.minis.beans.factory.support.ListableBeanFactory;
import com.minis.beans.processor.BeanFactoryPostProcessor;
import com.minis.core.env.Environment;
import com.minis.core.env.EnvironmentCapable;
import com.minis.event.ApplicationEventPublisher;
import com.minis.exception.BeansException;

/**
 * @Author huangyulu
 * @Date 2026/1/19 17:31
 * @Description
 *  公共接口，支持上下文环境和事件发布
 *  所有上下文实现都基于当前接口
 */
public interface ApplicationContext extends EnvironmentCapable,
        ListableBeanFactory, ConfigurableBeanFactory, ApplicationEventPublisher {


    /*
    * 1. 抽取ApplicationContext接口，实现更多有关上下文的内容
    * 2. 支持事件的发布与监听
    * 3. 新增AbstractApplicationContext，规范刷新上下文refresh方法的步骤规则，
    *    且将每一步骤进行抽象，提供默认实现类，同时支持自定义
    * 4. 完成刷新之后发布事件
    *
    * */

    String getApplicationName();

    long getStartupDate();

    ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;

    void setEnvironment(Environment environment);

    Environment getEnvironment();

    void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor);

    void refresh() throws BeansException, IllegalStateException;

    void close();

    boolean isActive();
}
