package com.minis.beans.processor;

import com.minis.beans.annotation.Autowired;
import com.minis.beans.factory.AutowireCapableBeanFactory;
import com.minis.exception.BeansException;

import java.lang.reflect.Field;

/**
 * @Author huangyulu
 * @Date 2026/1/18 20:49
 * @Description
 */
public class AutowiredAnnotationBeanPostProcessor implements BeanPostProcessor {


    private AutowireCapableBeanFactory beanFactory;

    public void setBeanFactory(AutowireCapableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public AutowireCapableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    /**
     * bean初始化前处理
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Object result = bean;

        Class<?> clazz = bean.getClass();
        // 获取Bean的所有属性
        Field[] fields = clazz.getDeclaredFields();
        if (fields != null && fields.length > 0) {
            // 对每个属性进行判断，如果带有@Autowired注解则进行处理
            for (Field field : fields) {
                boolean isAutowired = field.isAnnotationPresent(Autowired.class);
                if (isAutowired) {
                    // 存在@Autowired注解
                    // 根据属性名，查找同名Bean
                    String fieldName = field.getName();
                    Object autowiredObj = this.getBeanFactory().getBean(fieldName);
                    // 设置属性值，完成注入
                    field.setAccessible(true);
                    try {
                        field.set(bean, autowiredObj);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }

                }

            }
        }


        return result;
    }

    /**
     * bean初始化后处理
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }
}
