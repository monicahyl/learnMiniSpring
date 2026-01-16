package com.minis.test.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author huangyulu
 * @Date 2026/1/15 11:26
 * @Description
 *
 *
 * 反射是 Java 在运行时（Runtime）动态获取类的信息，并操作类的能力。
 *
 * 也就是说：
 * 平时你写的代码是 编译期就确定了类、方法、字段，而反射可以在 程序运行时：
 * 	•	获取类的结构（类名、字段、方法、构造器、注解等）
 * 	•	动态创建对象
 * 	•	动态调用方法
 * 	•	动态访问或修改字段
 *
 * 	所以，类一定要存在
 *
 */
public class BaseReflection {


    private String name;

    private Integer age;


    public BaseReflection() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return age;
    }

    public void setValue(Integer age) {
        this.age = age;
    }

    public static void main(String[] args) {
        // 动态创建对象
        try {
            Class<?> aClass = Class.forName("com.minis.test.reflection.BaseReflection");
            Object obj = aClass.newInstance(); // 调用无参构造,创建对象


            Method[] methods = aClass.getMethods();
            for (Method method : methods) {
                System.out.println(method.getName());
            }

            Constructor<?>[] constructors = aClass.getConstructors();
            for (Constructor<?> constructor : constructors) {
                System.out.println(constructor.getName());
            }

            Method setNameMethod = aClass.getMethod("setName", String.class);
            setNameMethod.invoke(obj, "Monica"); // 相当于 obj.setName("Monica")

            // 动态访问或修改字段
            Field field = aClass.getDeclaredField("age");
            field.setAccessible(true); // 私有字段必须先设置可访问
            field.set(obj, 10); // 给obj.age赋值
            Object ageValue = field.get(obj); // 获取obj.age的值
            System.out.println(ageValue);


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

    }
}
