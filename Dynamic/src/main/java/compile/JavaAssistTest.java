package compile;

import javassist.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class JavaAssistTest {
    public static void main(String[] args) throws NotFoundException, IOException, CannotCompileException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        //获取到class定义的容器ClassPool
        ClassPool pool = ClassPool.getDefault();
        //创建类
        CtClass ss = pool.makeClass("ljl.GeneratedClass");

        //创建属性
        CtField f1 = CtField.make("private int age;", ss);
        CtField f2 = CtField.make("private String name;", ss);
        ss.addField(f1);
        ss.addField(f2);

        //创建方法
        CtMethod m1 = CtMethod.make("public String getName(){return name;}", ss);
        CtMethod m2 = CtMethod.make("public void setName(String name){this.name=name;}", ss);
        ss.addMethod(m1);
        ss.addMethod(m2);

        //添加构造器
        CtConstructor constructor = new CtConstructor(new CtClass[]{CtClass.intType, pool.get("java.lang.String")}, ss);
        constructor.setBody("{this.age=age; this.name=name;}");
        ss.addConstructor(constructor);

        constructor = new CtConstructor(new CtClass[]{}, ss);
        constructor.setBody("{}");
        ss.addConstructor(constructor);

        //直接打开可以看到结果
        ss.writeFile("./target/classes");
        System.out.println("生成类，成功！");


        //动态加载
        //加载class时要告诉你的classloader去哪个位置加载class
        URL[] urls = new URL[]{new URL("file:/" + System.getProperty("user.dir") + "/target/classes")};
        ClassLoader classLoader = new URLClassLoader(urls);

        //加载出错大概率代码写错了，动态编译貌似没有语法检查一类的
        Class<?> clazz = classLoader.loadClass("ljl.GeneratedClass");

        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
        }

        Object testClass = clazz.newInstance();
        //这边要指定类型才能找到
        Method setName = clazz.getDeclaredMethod("setName", String.class);
        Method getName = clazz.getDeclaredMethod("getName");

        System.out.println("getName: " + getName.invoke(testClass));
        setName.invoke(testClass, "sdfs");
        System.out.println("getName: " + getName.invoke(testClass));

        //创建接口实现类
        ClassPool classPool = ClassPool.getDefault();

        CtClass ctClass = classPool.makeClass("ljl.InterfaceImpl");
        CtMethod m3 = CtMethod.make("public void test(){System.out.println(\"interface test\");}", ctClass);
        ctClass.addMethod(m3);

        constructor = new CtConstructor(new CtClass[]{}, ctClass);
        constructor.setBody("{}");
        ctClass.addConstructor(constructor);

        ctClass.setInterfaces(new CtClass[]{classPool.get(AssistTest.class.getName())});

        AssistTest test = (AssistTest) ctClass.toClass().newInstance();
        test.test();
    }
}
