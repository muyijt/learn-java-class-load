package org.muyi.ljcl.classloader;

import java.io.*;

/**
 * 自定义类加载器
 */
public class MyClassLoader extends ClassLoader {
    private final String classPath;   // 存放 .class 文件的根目录

    public MyClassLoader(String classPath) {
        this.classPath = classPath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String fileName = classPath + File.separatorChar
                + name.replace('.', File.separatorChar) + ".class";
        try (InputStream is = new FileInputStream(fileName); ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            byte[] buf = new byte[1024];
            int len;
            while ((len = is.read(buf)) != -1) bos.write(buf, 0, len);
            byte[] classBytes = bos.toByteArray();
            // 把字节数组变成 Class 对象
            return defineClass(name, classBytes, 0, classBytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException(name, e);
        }
    }
}
