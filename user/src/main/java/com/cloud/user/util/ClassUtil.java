package com.cloud.user.util;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author: yangjie
 * @date: Created in 2020/5/27 13:35
 */
public class ClassUtil {

    public static Set<Class> getClasses(String packagePath){
        Set<Class> classes = new HashSet<>();
        String path = packagePath.replace(".","/");
        URL url = Thread.currentThread().getContextClassLoader().getResource(path);
        if(url == null){
            return classes;
        }
        //文件属性
        String protocol = url.getProtocol();
        if("file".equalsIgnoreCase(protocol)){
            classes.addAll(getFileClasses(url, packagePath));
        }else if("jar".equalsIgnoreCase(protocol)){
            try {
                Set<Class> sets = getJarClasses(url, packagePath);
                classes.addAll(sets);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return classes;
    }

    private static Set<Class> getFileClasses(URL url,String packagePath){
        Set<Class> classes = new HashSet<>();
        String FilePath = url.getFile();
        File file = new File(FilePath);
        String[] list = file.list();
        if(list == null){
            return classes;
        }
        for (String classPath : list){
            if(classPath.endsWith(".class")){
                classPath = classPath.replace(".class","");
                try {
                    Class<?> clazz = Class.forName(packagePath + "."+classPath);
                    classes.add(clazz);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }else {
                classes.addAll(getClasses(packagePath + "." + classPath));
            }
        }
        return classes;
    }

    // 扫描jar包
    private static Set<Class> getJarClasses(URL url, String packagePath) throws IOException {
        Set<Class> res = new HashSet<>();
        JarURLConnection conn = (JarURLConnection) url.openConnection();
        if (conn != null) {
            JarFile jarFile = conn.getJarFile();
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                String name = jarEntry.getName();
                if (name.contains(".class") && name.replaceAll("/", ".").startsWith(packagePath)) {
                    String className = name.substring(0, name.lastIndexOf(".")).replace("/", ".");
                    try {
                        Class clazz = Class.forName(className);
                        res.add(clazz);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Set<Class> sets = ClassUtil.getClasses("com.cloud.user.feign");
        for (Class clazz : sets){
            System.out.println(clazz.getName());
        }
    }

}
