package org.muyi.ljcl;

import org.muyi.ljcl.classloader.MyClassLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SpringBootApplication
public class LjclApp {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        SpringApplication.run(LjclApp.class, args);

        // 1. 创建自定义加载器，指定 .class 根目录
        MyClassLoader myLoader = new MyClassLoader("/home/muyijt0");

        // 2. 加载 org.muyi.ljcl.classloader.LoadedClass
        Class<?> clazz = myLoader.loadClass("org.muyi.ljcl.classloader.LoadedClass");

        // 3. 反射调用 hello 方法
        Method helloMethod = clazz.getMethod("hello");
        helloMethod.invoke(clazz.getDeclaredConstructor().newInstance(), null);

        // 4. 验证：类与系统加载器不同
        System.out.println("当前类加载器: " + LjclApp.class.getClassLoader());
        System.out.println("LoadedClass 的加载器   : " + clazz.getClassLoader());
    }
}
