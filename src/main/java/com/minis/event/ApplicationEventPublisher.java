package com.minis.event;

/**
 * @Author huangyulu
 * @Date 2026/1/16 14:24
 * @Description 发布事件
 */
public interface ApplicationEventPublisher {

    void publishEvent(ApplicationEvent event);

    void addApplicationListener(ApplicationListener listener);
}
