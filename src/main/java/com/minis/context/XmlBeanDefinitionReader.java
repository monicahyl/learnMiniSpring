package com.minis.context;

import com.minis.beans.BeanDefinition;
import com.minis.beans.SimpleBeanFactory;
import org.dom4j.Element;

/**
 * @Author huangyulu
 * @Date 2026/1/16 10:48
 * @Description
 */
public class XmlBeanDefinitionReader {
    private SimpleBeanFactory simpleBeanFactory;

    public XmlBeanDefinitionReader(SimpleBeanFactory simpleBeanFactory) {
        this.simpleBeanFactory = simpleBeanFactory;
    }

    public XmlBeanDefinitionReader() {

    }

    /**
     * 把解析的XML内容转换成BeanDefinition，并加载到BeanFactory
     * @param resource
     */
    public void loadBeanDefinitions(Resource resource) {
        while (resource.hasNext()) {
            Element element = (Element) resource.next();
            String beanID = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");

            BeanDefinition beanDefinition = new BeanDefinition(beanID, beanClassName);
            this.simpleBeanFactory.registerBeanDefinition(beanDefinition);
        }
    }

}
