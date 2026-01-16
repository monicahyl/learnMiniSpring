package com.minis;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.*;

/**
 * @Author huangyulu
 * @Date 2026/1/14 19:30
 * @Description 1. 解析xml文件，获取Bean信息
 * 2. 利用反射创建Bean实例
 */
public class ClassPathXmlApplicationContextBase {

    private static final Logger log = LoggerFactory.getLogger(ClassPathXmlApplicationContextBase.class);

    private List<BeanDefinition> beanDefinitions = new ArrayList<>();
    private Map<String, Object> singletons = new HashMap<>();

    /**
     * 构造器获取外部配置，解析Bean定义，形成内存映像
     *
     * @param fileName
     */
    public ClassPathXmlApplicationContextBase(String fileName) {
        log.info("ClassPathXmlApplicationContext fileName={}", fileName);
        this.readXml(fileName);
        this.instanceBeans();
    }

    /**
     * 解析xml,获取Bean信息
     *
     * @param fileName
     */
    private void readXml(String fileName) {
        SAXReader saxReader = new SAXReader();
        // 用当前类的类加载器，从「classpath」中查找资源文件
        // getResource("application.yml") -> classpath:/application.yml
        URL xmlPath = this.getClass().getClassLoader().getResource(fileName);
        log.info("readXml() xmlPath={}", xmlPath);
        try {
            Document document = saxReader.read(xmlPath);
            Element root = document.getRootElement(); // 获取根节点：<beans>
            log.info("root name={}", root.getName());

            List<Element> elements = root.elements();
            log.info("elements.size={}", elements.size());
            for (Element element : elements) {
                log.info("element name={}", element.getName());
                String beanId = element.attributeValue("id");
                String beanClassName = element.attributeValue("class");
                log.info("beanId={}, beanClassName={}", beanId, beanClassName);

                // 创建Bean对象
                BeanDefinition beanDefinition = new BeanDefinition(beanId, beanClassName);
                beanDefinitions.add(beanDefinition);
            }


        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 利用反射创建Bean实例，并存储在singletons中
     */
    private void instanceBeans() {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            String className = beanDefinition.getClassName();
            log.info("ClassPathXmlApplicationContext instanceBeans() className={}", className);
            try {
                Object object = Class.forName(className).newInstance(); // 调用无参构造,动态创建对象
                singletons.put(beanDefinition.getId(), object); // 类别名，类对象
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 对外暴露，支持外部程序从容器中获取Bean实例
     * -> 会逐步演化成核心方法
     * @param beanName
     * @return
     */
    public Object getBean(String beanName) {
        return singletons.get(beanName);
    }


    public static void main(String[] args) {

        ClassLoader cl = ClassPathXmlApplicationContextBase.class.getClassLoader();

        System.out.println("classpath root = " + cl.getResource(""));
        System.out.println("classpath root = " + cl.getResource("beans.xml"));

        URL xmlPath = ClassPathXmlApplicationContextBase.class.getClassLoader().getResource("beans.xml");
        System.out.println("xmlPath = " + xmlPath);

        ClassPathXmlApplicationContextBase classPathXmlApplicationContext = new ClassPathXmlApplicationContextBase("beans.xml");
    }


}
