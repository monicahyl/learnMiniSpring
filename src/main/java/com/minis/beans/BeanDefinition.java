package com.minis.beans;

import com.minis.beans.argument.ArgumentValues;
import com.minis.beans.argument.PropertyValues;

/**
 * @Author huangyulu
 * @Date 2026/1/14 17:27
 * @Description 定义Bean
 */
public class BeanDefinition {

//    单例
    public static final String SCOPE_SINGLETON = "singleton";
//    非单例
    public static final String SCOPE_PROTOTYPE = "prototype";

    // 懒加载：容器不会在启动时立即创建 Bean，而是在第一次使用（注入或 getBean）时才创建实例。
    // lazy-init="false"（默认） → 容器启动时创建
    // lazy-init="true" → 延迟初始化
    private boolean lazyInit = false;

    private String[] dependsOn;

    private ArgumentValues constructorArgumentValues;

    private PropertyValues propertyValues;

    private String initMethodName;

    private volatile Object beanClass;


    // 类别名
    private String id;

    // 类名
    private String className;

    private String scope = SCOPE_SINGLETON;

    public BeanDefinition(String id, String className) {
        this.id = id;
        this.className = className;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public BeanDefinition() {

    }

    public boolean isLazyInit() {
        return lazyInit;
    }

    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    public String[] getDependsOn() {
        return dependsOn;
    }

    public void setDependsOn(String[] dependsOn) {
        this.dependsOn = dependsOn;
    }

    public ArgumentValues getConstructorArgumentValues() {
        return constructorArgumentValues;
    }

    public void setConstructorArgumentValues(ArgumentValues constructorArgumentValues) {
        this.constructorArgumentValues = constructorArgumentValues;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }

    public String getInitMethodName() {
        return initMethodName;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public Object getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Object beanClass) {
        this.beanClass = beanClass;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public boolean isSingleton() {
        return SCOPE_SINGLETON.equals(scope);
    }

    public boolean isPrototype() {
        return SCOPE_PROTOTYPE.equals(scope);
    }
}
