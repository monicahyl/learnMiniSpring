package com.minis.context;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.Iterator;

/**
 * @Author huangyulu
 * @Date 2026/1/16 10:41
 * @Description 解析xml文件
 */
public class ClassPathXmlResource implements Resource {


    private Document document;

    private Element rootElement;

    Iterator<Element> elementIterator;

    public ClassPathXmlResource(String fileName) {
        SAXReader saxReader = new SAXReader();
        InputStream xmlPath = this.getClass().getClassLoader().getResourceAsStream(fileName);
        // 将配置文件装载进来，生成一个迭代器，可以用于遍历
        try {
            this.document = saxReader.read(xmlPath);
            this.rootElement = document.getRootElement();
            this.elementIterator = this.rootElement.elementIterator();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public ClassPathXmlResource() {

    }

    @Override
    public boolean hasNext() {
        return this.elementIterator.hasNext();
    }

    @Override
    public Object next() {
        return this.elementIterator.next();
    }
}
