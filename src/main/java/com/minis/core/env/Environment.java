package com.minis.core.env;

/**
 * @Author huangyulu
 * @Date 2026/1/19 17:00
 * @Description 获取属性
 */
public interface Environment extends PropertyResolver {

    String[] getActiveProfiles();

    String[] getDefaultProfiles();

    boolean acceptsProfile(String... profiles);
}
