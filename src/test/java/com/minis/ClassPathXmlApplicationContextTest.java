package com.minis;

import com.minis.ClassPathXmlApplicationContext;
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

    private static final Logger log = LoggerFactory.getLogger(ClassPathXmlApplicationContext.class);


    @Test
    public void testReadXml() {
//        System.out.println("readXml");
//        log.info("readXml");
        ClassPathXmlApplicationContext ctx =
                new ClassPathXmlApplicationContext("beans.xml");

        AService aservice = (AService) ctx.getBean("aservice");
        aservice.sayHello();

    }

}
