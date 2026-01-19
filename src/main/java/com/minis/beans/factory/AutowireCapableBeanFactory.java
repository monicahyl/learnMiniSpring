package com.minis.beans.factory;

import com.minis.beans.AbstractBeanFactory;
import com.minis.beans.processor.AutowiredAnnotationBeanPostProcessor;
import com.minis.exception.BeansException;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author huangyulu
 * @Date 2026/1/18 20:50
 * @Description
 */
public class AutowireCapableBeanFactory extends AbstractBeanFactory {

    /**
     * 在Bean初始化后处理的processor
     */
    private final List<AutowiredAnnotationBeanPostProcessor> beanPostProcessors = new ArrayList<>();

    public void addBeanPostProcessor(AutowiredAnnotationBeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public int getBeanPostProcessorCount() {
        return this.beanPostProcessors.size();
    }

    public List<AutowiredAnnotationBeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }


    @Override
    public Object applyBeanPostProcessorBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result =  existingBean;
        for (AutowiredAnnotationBeanPostProcessor beanPostProcessor : beanPostProcessors) {
            beanPostProcessor.setBeanFactory(this);
            result = beanPostProcessor.postProcessBeforeInitialization(result, beanName);

            if (result == null) {
                return result;
            }
        }
        return result;
    }


    @Override
    public Object applyBeanPostProcessorAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result =  existingBean;
        for (AutowiredAnnotationBeanPostProcessor beanPostProcessor : beanPostProcessors) {
            result = beanPostProcessor.postProcessAfterInitialization(result, beanName);

            if (result == null) {
                return result;
            }
        }
        return result;
    }
}
