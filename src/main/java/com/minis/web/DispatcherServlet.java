package com.minis.web;

import com.minis.web.anno.RequestMapping;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
//    private Map<String, Object> mappingObjs = new HashMap<>();

    // 存储需要扫描的package列表
    private List<String> packageNames = new ArrayList<>();
    // 存储Controller名称数组列表
    private List<String> controllerNames = new ArrayList<>();
    // 存储 Controller名称 - Controller类 的映射关系
    private Map<String, Class<?>> controllerClasses = new HashMap<>();
    // 存储 Controller名称 - Controller类对象 的映射关系
    private Map<String, Object> controllerObjs = new HashMap<>();
    // 保存自定义的 @RequestMapping名称（URL的名称） 的列表
    private List<String> urlMappingNames = new ArrayList<>();
    // 存储 URL名称-对象 的映射关系
    private Map<String, Object> mappingObjs = new HashMap<>();
    // 存储 URL名称-方法 的映射关系
    private Map<String, Method> mappingMethods = new HashMap<>();


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


        this.packageNames = XmlScanComponentHelper.getNodeValue(xmlPath);
        refresh();
    }

    // 对所有mappingValues中注册的类进行实例化，默认构造函数
    protected void refresh() {
        initController();
        initMapping();
    }

    protected void initController() {
        // 扫描包，获取所有类名
        this.controllerNames = scanPackages(this.packageNames);
        for (String controllerName : this.controllerNames) {
            Object obj = null;
            Class<?> clz = null;
            try {
                clz = Class.forName(controllerName); // 加载类
                this.controllerClasses.put(controllerName, clz);

                obj = clz.newInstance(); // 实例化Bean
                this.controllerObjs.put(controllerName, obj);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }


        }

    }

    protected void initMapping() {
        for (String controllerName : this.controllerNames) {
            Class<?> clazz = this.controllerClasses.get(controllerName);
            Object obj = this.controllerObjs.get(controllerName);

            Method[] methods = clazz.getDeclaredMethods();
            if (methods != null) {
                for (Method method : methods) {
                    // 检查所有方法
                    boolean isPresent = method.isAnnotationPresent(RequestMapping.class);
                    if (isPresent) {
                        // 存在@RequestMapping注解
                        String methodName = method.getName();
                        // 建立方法名和URL的映射
                        String urlMapping = method.getAnnotation(RequestMapping.class).value();

                        this.urlMappingNames.add(urlMapping);
                        this.mappingObjs.put(urlMapping, obj);
                        this.mappingMethods.put(urlMapping, method);
                    }
                }
            }

        }
    }


    private List<String> scanPackages(List<String> packages) {
        List<String> tempControllerNames = new ArrayList<>();
        for (String packageName : packages) {
            tempControllerNames.addAll(scanPackage(packageName));
        }
        return tempControllerNames;
    }


    private List<String> scanPackage(String packageName) {
        List<String> tempControllerNames = new ArrayList<>();

        URI uri = null;
        try {
            // 将以.分割的包名换成/分割的uri
            uri = this.getClass().getResource("/" + packageName.replaceAll("\\.", "/")).toURI();
            System.out.println(uri.toString());

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        // 处理对应的文件目录
        File dir = new File(uri);
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                scanPackage(packageName + "." + file.getName());
            } else {
                String controllerName = packageName + "." + file.getName().replace(".class", "");
                tempControllerNames.add(controllerName);
            }
        }
        return tempControllerNames;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath(); // 获取请求的path

        if (!this.urlMappingNames.contains(servletPath)) {
            return;
        }

        Object obj = null;
        Object objResult = null;


        Method method = this.mappingMethods.get(servletPath);
        obj = this.mappingObjs.get(servletPath);
        try {
            objResult = method.invoke(obj);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

//        将方法返回值写入Response
        resp.getWriter().append(objResult.toString());
    }
}
