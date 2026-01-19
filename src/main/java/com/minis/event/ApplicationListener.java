package com.minis.event;

import java.util.EventListener;

/**
 * @Author huangyulu
 * @Date 2026/1/19 17:24
 * @Description
 */
public class ApplicationListener implements EventListener {

    void onApplicationEvent(ApplicationEvent event) {
        System.out.println(event.toString());
    }
}
