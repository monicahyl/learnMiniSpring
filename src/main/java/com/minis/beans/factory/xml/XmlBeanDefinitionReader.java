package com.minis.beans.factory.xml;

import com.minis.beans.factory.config.BeanDefinition;
import com.minis.beans.factory.support.SimpleBeanFactory;
import com.minis.beans.factory.config.ConstructorArgumentValue;
import com.minis.beans.factory.config.ConstructorArgumentValues;
import com.minis.beans.argument.PropertyValue;
import com.minis.beans.argument.PropertyValues;
import com.minis.context.Resource;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

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
            // 处理Bean
            Element element = (Element) resource.next();
            String beanID = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanID, beanClassName);

            // 处理属性
            List<Element> propertyElements = element.elements("property");
            PropertyValues pvs = new PropertyValues();
            List<String> refs = new ArrayList<>();
            for (Element propertyElement : propertyElements) {
                String type = propertyElement.attributeValue("type");
                String name = propertyElement.attributeValue("name");
                String value = propertyElement.attributeValue("value");
                String ref = propertyElement.attributeValue("ref");

                String newValue = "";
                boolean isRef = false;
                if (value != null && !value.equals("")) {
                    newValue = value;
                    isRef = false;
                } else if (ref != null && !ref.equals("")) {
                    newValue = ref;
                    isRef = true;
                    refs.add(ref);
                }

                pvs.addPropertyValue(new PropertyValue(type, name, newValue, isRef));
            }

            beanDefinition.setPropertyValues(pvs);
            String[] refArray = refs.toArray(new String[0]);
            beanDefinition.setDependsOn(refArray); // todo:

            // 处理构造器参数
            List<Element> constructorElements = element.elements("constructor-args");
            ConstructorArgumentValues avs = new ConstructorArgumentValues();
            for (Element constructorElement : constructorElements) {
                String type = constructorElement.attributeValue("type");
                String name = constructorElement.attributeValue("name");
                String value = constructorElement.attributeValue("value");
                avs.addArgumentValue(new ConstructorArgumentValue(type, name, value));
            }

            beanDefinition.setConstructorArgumentValues(avs);


            this.simpleBeanFactory.registerBeanDefinition(beanID, beanDefinition);
        }
    }

}
