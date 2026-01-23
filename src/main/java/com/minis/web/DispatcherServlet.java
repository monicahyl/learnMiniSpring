package com.minis.web;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author huangyulu
 * @Date 2026/1/23 14:47
 * @Description MVC的核心启动类，完成URL映射机制
 */
public class DispatcherServlet extends HttpServlet {

    // MappingValue对象
    private Map<String, MappingValue> mappingValues;

    // 对应的类
    private Map<String, Class<?>> mappingClz = new HashMap<>();

    // 实例对象
    private Map<String, Object> mappingObjs = new HashMap<>();


    /**
     * 初始化方法
     * 1. 处理从外部传入的资源
     * 2. 将xml文件内容解析后存入mappingValues内
     * 3. 最后调用Refresh()函数创建Bean
     *
     * @param servletConfig
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println("DispatcherSevlet init");
        super.init(servletConfig);

        String contextConfigLocation = servletConfig.getInitParameter("contextConfigLocation");
        URL xmlPath = null;
        try {
           xmlPath = this.getServletContext().getResource(contextConfigLocation);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Resource rs = new ClassPathXmlResource(xmlPath);
        XmlConfigReader reader = new XmlConfigReader();
        mappingValues = reader.loadConfig(rs);
        Refresh();
    }

    // 对所有mappingValues中注册的类进行实例化，默认构造函数
    protected void Refresh() {
        for (Map.Entry<String, MappingValue> entry : mappingValues.entrySet()) {
            String uri = entry.getKey();
            String className = entry.getValue().getClz();
            Object obj = null;
            Class<?> clz = null;
            try {
                clz = Class.forName(className);
                obj = clz.newInstance();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            mappingClz.put(uri, clz);
            mappingObjs.put(uri, obj);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath(); // 获取请求的path
        if (this.mappingValues.get(servletPath) == null) {
            return;
        }

        Class<?> clz = this.mappingClz.get(servletPath); // 获取Bean类定义
        Object obj = this.mappingObjs.get(servletPath); // 获取Bean实例
        String methodName = this.mappingValues.get(servletPath).getMethod(); // 获取调用方法名
        Object objResult = null;
        try {
            Method method = clz.getMethod(methodName);
            objResult = method.invoke(obj); // 方法调用
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        // 将方法返回值写入Response
        resp.getWriter().append(objResult.toString());
    }
}
