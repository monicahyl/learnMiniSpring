package com.minis.event;

/**
 * @Author huangyulu
 * @Date 2026/1/19 17:25
 * @Description
 */
public class ContextRefreshEvent extends ApplicationEvent {

    /**
     * Constructs a prototypical Event.
     *
     * @param arg0 The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ContextRefreshEvent(Object arg0) {
        super(arg0);
    }

    public String toString() {
        return this.msg;
    }
}
