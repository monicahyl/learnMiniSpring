package com.minis.web;

import org.dom4j.Element;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author huangyulu
 * @Date 2026/1/21 17:24
 * @Description
 */
@Deprecated
public class XmlConfigReader {

    public XmlConfigReader() {

    }

    public Map<String, MappingValue> loadConfig(Resource res) {
        Map<String, MappingValue> mappings = new HashMap<>();

        while (res.hasNext()) {
            // 读所有的节点，解析ID，class和Value
            Element element = (Element) res.next();
            String beanID = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            String beanMethod = element.attributeValue("value");

            mappings.put(beanID, new MappingValue(beanID, beanClassName, beanMethod));
        }
        return mappings;
    }
}
