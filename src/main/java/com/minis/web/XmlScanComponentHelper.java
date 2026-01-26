package com.minis.web;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Author huangyulu
 * @Date 2026/1/23 17:19
 * @Description
 */
public class XmlScanComponentHelper {

    public static List<String> getNodeValue(URL xmlPath) {
        List<String> packages = new ArrayList<>();
        SAXReader saxReader = new SAXReader();

        Document document = null;
        try {
            document = saxReader.read(xmlPath);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        Element rootElement = document.getRootElement();
        Iterator iterator = rootElement.elementIterator();
        while (iterator.hasNext()) {
            Element element = (Element) iterator.next();
            String pack = element.attributeValue("base-package");
            packages.add(pack);
        }
        return packages;
    }
}
