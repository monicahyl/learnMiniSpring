package com.minis.event;

import java.util.EventObject;

/**
 * @Author huangyulu
 * @Date 2026/1/16 14:24
 * @Description 监听应用的事件
 */
public class ApplicationEvent extends EventObject {


    private static final long serialVersionUID = 1L;

    protected String msg = null;


    /**
     * Constructs a prototypical Event.
     *
     * @param arg0 The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ApplicationEvent(Object arg0) {
        super(arg0);
        this.msg = arg0.toString();
    }
}
