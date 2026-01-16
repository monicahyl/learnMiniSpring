package com.minis;

import com.minis.context.ClassPathXmlApplicationContext;
import com.minis.exception.BeansException;
import com.minis.test.service.AService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author huangyulu
 * @Date 2026/1/15 10:29
 * @Description
 */
public class ClassPathXmlApplicationContextTest {

    private static final Logger log = LoggerFactory.getLogger(ClassPathXmlApplicationContextBase.class);


    @Test
    public void testReadXml() {
//        System.out.println("readXml");
//        log.info("readXml");
        ClassPathXmlApplicationContextBase ctx =
                new ClassPathXmlApplicationContextBase("beans.xml");

        AService aservice = (AService) ctx.getBean("aservice");
        aservice.sayHello();

    }

    @Test
    public void test() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        AService aservice = null;
        try {
            aservice = (AService) context.getBean("aservice");
            aservice.sayHello();


        } catch (BeansException e) {
            e.printStackTrace();
        }

    }

}
