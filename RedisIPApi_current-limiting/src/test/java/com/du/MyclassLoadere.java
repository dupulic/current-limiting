package com.du;

import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class MyclassLoadere extends ClassLoader{
    //key类名，byte字节码
    private Map<String,byte[]> clazz;
    public MyclassLoadere(String ... files) throws IOException {
        System.out.println(files);
        //通用加载全部，也可以只加载自己需要的
        clazz = new HashMap<>();
        for (String file:files){
            //解析处理 xxx/sdfs/fgfg/xss.class ===》 com.sdfs.
            JarFile jarFile = new JarFile(file);
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()){
                JarEntry jarEntry = entries.nextElement();
                if (jarEntry.getName().endsWith(".class")){
                    try (InputStream inputStream=jarFile.getInputStream(jarEntry)){
                        int pos = 0;
                        int len;
                        byte[] bytes = new byte[inputStream.available()];
                        while ((len=inputStream.read(bytes,pos,bytes.length-pos))>0){
                            pos+=len;
                        }
                        String className = jarEntry.getName().replace("/", ".").replace(".class", "");
                        clazz.put(className,bytes);
                    }
                }
            }
        }
    }
   @Override
    public Class<?> loadClass(String name,boolean resolve) throws ClassNotFoundException {
       synchronized (getClassLoadingLock(name)){
           Class<?> loadedClass = findLoadedClass(name);
           //如果loadedClass==null,则直接从map获取
           if (loadedClass==null){
               byte[] bytes = clazz.get(name);
               //null则找父类加载
               if (bytes==null){
                   super.loadClass(name,resolve);
               }
               else {
                   //二进制转化为字节码文件
                   loadedClass=defineClass(name,bytes,0,bytes.length);
               }
           }
           return loadedClass;
       }
    }

    public static void main(String[] args) {
        test1();
//        try {
//            test2();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
    }

    public static void test1(){
        System.out.println(StringUtils.isEmpty("sdf"));
    }

    public static void test2() throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        MyclassLoadere myclassLoadere = new MyclassLoadere("C:\\狂神java三一\\spring-MVC\\out\\artifacts\\springmvc_07_file_war_exploded\\WEB-INF\\lib\\javax.servlet-api-4.0.1.jar");
        Class<?> aClass = myclassLoadere.loadClass("org.springframework.util.StringUtils");
        System.out.println(aClass.getClassLoader().getClass().getName());
        Method isEmpty = aClass.getMethod("isEmpty", CharSequence.class);
        System.out.println(isEmpty.invoke(null,"fffw"));
    }
}
