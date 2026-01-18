package com.minis;

import com.minis.context.ClassPathXmlApplicationContext;
import com.minis.exception.BeansException;
import com.minis.test.service.AService;
import com.minis.test.service.BaseService;
import com.minis.test.service.CaseService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @Author huangyulu
 * @Date 2026/1/15 10:29
 * @Description
 */
public class ClassPathXmlApplicationContextTest {

    private static final Logger log = LoggerFactory.getLogger(ClassPathXmlApplicationContextBase.class);


    @Test
    public void testReadXml() throws BeansException {
//        System.out.println("readXml");
//        log.info("readXml");
        ClassPathXmlApplicationContext ctx =
                new ClassPathXmlApplicationContext("beans.xml");

        AService aservice = (AService) ctx.getBean("aservice");
        aservice.sayHello("aservice");

        BaseService baseService = (BaseService) ctx.getBean("baseservice");
        baseService.sayHello("baseservice");

        CaseService caseService = (CaseService) ctx.getBean("caseservice");
        caseService.sayHello("caseservice");

    }

    @Test
    public void test() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        AService aservice = null;
        try {
            aservice = (AService) context.getBean("aservice");
            aservice.sayHello("aservice");


        } catch (BeansException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void testClass() {
        try {

            Class<?> aClass1 = Class.forName("com.minis.test.service.impl.AServiceImpl");
            Method method = aClass1.getMethod("setBaseService", BaseService.class);
            System.out.println(method.getName());

//            aClass1.getMethod("setBaseService", Class.forName("com.minis.test.service.impl.BaseServiceImpl"));
            aClass1.getMethod("setBaseService", Class.forName("com.minis.test.service.BaseService"));




        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }


    }
}
