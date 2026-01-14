package com.minis;

/**
 * @Author huangyulu
 * @Date 2026/1/14 17:27
 * @Description 定义Bean
 */
public class BeanDefinition {

    // 类别名
    private String id;

    // 类名
    private String className;

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
}
