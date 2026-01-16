package com.minis.context;

import com.minis.BeanDefinition;
import com.minis.beans.BeanFactory;
import org.dom4j.Element;

/**
 * @Author huangyulu
 * @Date 2026/1/16 10:48
 * @Description
 */
public class XmlBeanDefinitionReader {
    private BeanFactory beanFactory;

    public XmlBeanDefinitionReader(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
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
            this.beanFactory.registerBeanDefinition(beanDefinition);
        }
    }

}
