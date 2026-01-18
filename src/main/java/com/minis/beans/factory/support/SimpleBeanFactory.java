package com.minis.beans.factory.support;

import com.minis.beans.factory.config.BeanDefinition;
import com.minis.beans.factory.config.ConstructorArgumentValue;
import com.minis.beans.factory.config.ConstructorArgumentValues;
import com.minis.beans.argument.PropertyValue;
import com.minis.beans.argument.PropertyValues;
import com.minis.beans.factory.BeanFactory;
import com.minis.exception.BeansException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author huangyulu
 * @Date 2026/1/16 12:06
 * @Description
 */
public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry {

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

    // "毛坯实例" -> 只有对象，属性还没注入
    private Map<String, Object> earlySingleObjects = new ConcurrentHashMap<>(256);

    private List<String> beanDefinitionNames = new ArrayList<>();

    public SimpleBeanFactory() {

    }


    /**
     * get bean,容器核心方法
     * <p>
     * 1. 尝试获取Bean实例
     * 2. 获取不到，依据Bean定义来创建实例
     * 3. 注册Bean实例
     *
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object getBean(String beanName) throws BeansException {
        // 尝试获取Bean实例
        Object singleton = this.getSingleton(beanName);
        if (singleton == null) {
            // 如果没有实例，则尝试从毛坯实例中获取
            singleton = this.earlySingleObjects.get(beanName);
            if (singleton == null) {
                // 无毛坯实例 -> 创建Bean实例并注册
                // 获取Bean定义来创建实例
                BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
                singleton = createBean(beanDefinition);
                // 注册Bean实例
                this.registerSingleton(beanName, singleton);

                // 预留beanpostprocessor位置
                // step1: postProcessBeforeInitialization
                // step2: afterPropertiesSet
                // step3: init-method
                // step4: postProcessAfterInitialization

            }
        }

        return singleton;
    }

    /**
     * 构建Bean对象
     *
     * @param beanDefinition 1. 一部分是处理constructor的里面的参数
     *                       2. 另外一部分是处理各个property的属性
     *                       <p>
     *                       核心是通过Java的反射机制调用构造器及setter方法
     *                       在调用过程中根据具体的类型把属性值作为一个参数赋值进去
     *                       反射技术是IoC容器赖以工作的基础
     * @return
     */
    private Object createBean(BeanDefinition beanDefinition) {
        Class<?> clz = null;
        // 创建毛坯实例
        Object obj = doCreateBean(beanDefinition);
        // 存放到毛坯实例缓存中
        this.earlySingleObjects.put(beanDefinition.getId(), obj);
        try {
            clz = Class.forName(beanDefinition.getClassName());
            // 处理属性
            handleProperties(beanDefinition, clz, obj);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


//        try {
//            clz = Class.forName(beanDefinition.getClassName());
//            // 处理构造器参数
//            ConstructorArgumentValues argumentValues = beanDefinition.getConstructorArgumentValues();
//            if (!argumentValues.isEmpty()) {
//                // 有参数，需要处理
//                // 参数个数
//                int argumentCount = argumentValues.getArgumentCount();
//                // 参数类型
//                Class<?>[] paramTypes = new Class<?>[argumentCount];
//                // 参数值
//                Object[] paramValues = new Object[argumentCount];
//                // 分别处理每个参数，基于数据类型
//                for (int i = 0; i < argumentCount; i++) {
//                    ConstructorArgumentValue argumentValue = argumentValues.getIndexedArgumentValue(i);
//                    String argType = argumentValue.getType();
//                    Object argValue = argumentValue.getValue();
//
//                    if ("String".equals(argType) || "java.lang.String".equals(argType)) {
//                        paramTypes[i] = String.class;
//                        paramValues[i] = argValue;
//                    } else if ("Integer".equals(argType) || "java.lang.Integer".equals(argType)) {
//                        paramTypes[i] = Integer.class;
//                        paramValues[i] = Integer.valueOf(String.valueOf(argValue));
//                    } else if ("int".equals(argType)) {
//                        paramTypes[i] = int.class;
//                        paramValues[i] = Integer.valueOf(String.valueOf(argValue));
//                    } else {
//                        // default String
//                        paramTypes[i] = String.class;
//                        paramValues[i] = argValue;
//                    }
//                }
//                // 按照特定构造器构建实例
//                con = clz.getConstructor(paramTypes);
//                obj = con.newInstance(paramValues);
//            } else {
//                // 如果没有参数，直接创建实例
//                obj = clz.newInstance();
//            }
//
//
//            // 处理属性参数
////            handleProperties();
//            PropertyValues propertyValues = beanDefinition.getPropertyValues();
//            if (!propertyValues.isEmpty()) {
//                for (int i = 0; i < propertyValues.size(); i++) {
//                    // 基于数据类型，分别处理每个属性
//                    PropertyValue propertyValue = propertyValues.getPropertyValueList().get(i);
//                    String type = propertyValue.getType();
//                    String name = propertyValue.getName();
//                    Object value = propertyValue.getValue();
//                    boolean isRef = propertyValue.getIsRef();
//
//                    Class<?>[] paramTypes = new Class<?>[1];
//                    Object[] paramValues = new Object[1];
//
//                    if (isRef) {
//                        // 引用类型
//                        // type = com.minis.test.xxx
//                        Class<?> aClass = Class.forName(type);
//                        paramTypes[0] = aClass;
//                        // 创建依赖的bean
//                        paramValues[0] = getBean((String) value);
//                    } else {
//                        if ("String".equals(type) || "java.lang.String".equals(type)) {
//                            paramTypes[0] = String.class;
//                        } else if ("Integer".equals(type) || "java.lang.Integer".equals(type)) {
//                            paramTypes[0] = Integer.class;
//                        } else if ("int".equals(type)) {
//                            paramTypes[0] = int.class;
//                        } else {
//                            // default String
//                            paramTypes[0] = String.class;
//                        }
//
//
//                        paramValues[0] = value;
//                    }
//
//
//                    // 按照setXxxx规范查找setter方法，调用setter方法设置属性
//                    // age -> setAge
//                    String methodName = String.format("%s%s%s", "set", name.substring(0, 1).toUpperCase(), name.substring(1));
//
//                    // 注入参数
//                    Method method = clz.getMethod(methodName, paramTypes);
//                    method.invoke(obj, paramValues);
//                }
//            }
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (NoSuchMethodException e) {
//            throw new RuntimeException(e);
//        } catch (InvocationTargetException e) {
//            throw new RuntimeException(e);
//        } catch (InstantiationException e) {
//            throw new RuntimeException(e);
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//        } catch (BeansException e) {
//            throw new RuntimeException(e);
//        }

        return obj;

    }


    /**
     * 创建毛坯实例，仅仅调用构造方法，没有进行属性处理
     *
     * @param beanDefinition
     * @return
     */
    private Object doCreateBean(BeanDefinition beanDefinition) {
        Class<?> clz = null;
        Constructor<?> con = null;
        Object obj = null;
        try {
            clz = Class.forName(beanDefinition.getClassName());
            // 处理构造器参数
            ConstructorArgumentValues constructorArgumentValues = beanDefinition.getConstructorArgumentValues();
            if (!constructorArgumentValues.isEmpty()) {
                // 有参数，需要处理
                // 参数个数
                int argumentCount = constructorArgumentValues.getArgumentCount();
                // 参数类型
                Class<?>[] paramTypes = new Class<?>[argumentCount];
                // 参数值
                Object[] paramValues = new Object[argumentCount];
                // 分别处理每个参数，基于数据类型
                for (int i = 0; i < argumentCount; i++) {
                    ConstructorArgumentValue constructorArgumentValue = constructorArgumentValues.getIndexedArgumentValue(i);
                    String argType = constructorArgumentValue.getType();
                    Object argValue = constructorArgumentValue.getValue();

                    if ("String".equals(argType) || "java.lang.String".equals(argType)) {
                        paramTypes[i] = String.class;
                        paramValues[i] = argValue;
                    } else if ("Integer".equals(argType) || "java.lang.Integer".equals(argType)) {
                        paramTypes[i] = Integer.class;
                        paramValues[i] = Integer.valueOf(String.valueOf(argValue));
                    } else if ("int".equals(argType)) {
                        paramTypes[i] = int.class;
                        paramValues[i] = Integer.valueOf(String.valueOf(argValue));
                    } else {
                        // default String
                        paramTypes[i] = String.class;
                        paramValues[i] = argValue;
                    }
                }
                // 按照特定构造器构建实例
                con = clz.getConstructor(paramTypes);
                obj = con.newInstance(paramValues);
            } else {
                // 如果没有参数，直接创建实例
                obj = clz.newInstance();
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return obj;
    }


    /**
     * 处理属性
     *
     * @param beanDefinition
     * @param clz
     * @param obj
     */
    private void handleProperties(BeanDefinition beanDefinition, Class<?> clz, Object obj) {
        // 处理属性
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        try {
            // 有属性处理属性
            if (!propertyValues.isEmpty()) {
                for (int i = 0; i < propertyValues.size(); i++) {
                    // 基于数据类型，分别处理每个属性
                    PropertyValue propertyValue = propertyValues.getPropertyValueList().get(i);
                    String type = propertyValue.getType();
                    String name = propertyValue.getName();
                    Object value = propertyValue.getValue();
                    boolean isRef = propertyValue.getIsRef();

                    Class<?>[] paramTypes = new Class<?>[1];
                    Object[] paramValues = new Object[1];

                    if (isRef) {
                        // 引用类型
                        // type = com.minis.test.xxx
                        Class<?> aClass = Class.forName(type);
                        paramTypes[0] = aClass;
                        // 创建依赖的bean
                        paramValues[0] = getBean((String) value);
                    } else {
                        if ("String".equals(type) || "java.lang.String".equals(type)) {
                            paramTypes[0] = String.class;
                        } else if ("Integer".equals(type) || "java.lang.Integer".equals(type)) {
                            paramTypes[0] = Integer.class;
                        } else if ("int".equals(type)) {
                            paramTypes[0] = int.class;
                        } else {
                            // default String
                            paramTypes[0] = String.class;
                        }


                        paramValues[0] = value;
                    }


                    // 按照setXxxx规范查找setter方法，调用setter方法设置属性
                    // age -> setAge
                    String methodName = String.format("%s%s%s", "set", name.substring(0, 1).toUpperCase(), name.substring(1));

                    // 注入参数
                    Method method = clz.getMethod(methodName, paramTypes);
                    method.invoke(obj, paramValues);
                }
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (BeansException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean containBean(String name) {
        return false;
    }

    @Override
    public boolean isSingleton(String name) {
        return this.beanDefinitionMap.get(name).isSingleton();
    }

    @Override
    public boolean isPrototype(String name) {
        return this.beanDefinitionMap.get(name).isPrototype();
    }

    @Override
    public Class<?> getType(String name) {
        return this.beanDefinitionMap.get(name).getClass();
    }


    @Override
    public void registerBean(String beanName, Object obj) {
        this.registerSingleton(beanName, obj);
    }

    @Override
    public Boolean containsBean(String name) {
        return containsSingleton(name);
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition bd) {
        this.beanDefinitionMap.put(name, bd);
        this.beanDefinitionNames.add(name);

        // todo: 先注释，一次性加载完Bean的配置，在循环构建Bean，后续看怎么处理这块
/*        if (!bd.isLazyInit()) {
            // 容器启动时构建
            try {
                getBean(name);
            } catch (BeansException e) {
                e.printStackTrace();
            }
        }*/
    }

    @Override
    public void removeBeanDefinition(String name) {
        this.beanDefinitionMap.remove(name);
        this.beanDefinitionNames.remove(name);
        this.removeSingleton(name);
    }

    @Override
    public BeanDefinition getBeanDefinition(String name) {
        return this.beanDefinitionMap.get(name);
    }

    @Override
    public boolean containsBeanDefinition(String name) {
        return this.beanDefinitionMap.containsKey(name);
    }

    /**
     * 将容器中所有的Bean实例创建出来
     *
     *
     * 整个容器启动的入口函数
     */
    public void refresh() {
        for (String beanName : beanDefinitionNames) {
            try {
                getBean(beanName);
            } catch (BeansException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
