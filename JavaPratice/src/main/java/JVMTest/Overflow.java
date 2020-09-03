package JVMTest;

import java.io.File;
import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import DesignPatterns.ProxyPattern.Member;
import DesignPatterns.ProxyPattern.People;
import DesignPatterns.ProxyPattern.myInvocationHandler;

public class Overflow {

	private int stackLength = 1;
	private int threadLength = 1;
	
	//VM arguments -Xmx50m -Xms50m -Xmn20m
	//heap out of memory
    public  void heapOOM() {
        List<OOMObject> list = new ArrayList<OOMObject>();
        int i = 0;
        try {
            while(true) {
                // list�������ã�����Full GC ���� 
                list.add(new OOMObject());
                i++;
                if(i%100000 == 0) System.out.println(i + " objects");
            }
		} catch (Exception e) {
			// TODO: handle exception
			//can not catch exception
			//times mainly related to Xmx
			//decrease xmn is can trigger GC more times and new more object
			//too mall xmn cause GC overhead limit exceeded
			e.printStackTrace();
		}

    }
 
    class OOMObject {
 
    }
    
    
    
    // stack overflow 
    //-Xss128k this arguments is for each thread
    //increase -Xss increase the stackLength
    //Xss no influence on heap space
    public void stackLeak() {
        stackLength++;
        if(stackLength%500 == 0) System.out.println(stackLength + " stack levels");
        stackLeak();
    }
    
    

    private void dontStop() {
        while(true) {
        }
    }
 
    // ���̷߳�ʽ���ջ�ڴ���� OutOfMemoryError
    //ÿ���߳���Ҫһ��ջ�ռ䣬����Xss�������߳�����
    //���иô���������ϵͳ������������
    public void stackLeakByThread() {
        while(true) {
            Thread thread = new Thread(() -> dontStop());
            thread.start();
            threadLength++;
            if(threadLength%500 == 0) System.out.println(threadLength + " thread numbers");
        }
    }

    //�������������̬�����࣬����Ԫ�ռ������-XX:MetaspaceSize
    //JDK 1.7 PermGen OOM, JDK 1.8 Metaspace OOM
    //��ˣ����Դ�����֤ JDK 1.7 �� 1.8 ���ַ������������ô�ת�Ƶ����У����� JDK 1.8 ���Ѿ����������ô��Ľ��ۡ�
    //�����Ϣ����Ԫ�ռ䣬Ԫ�ռ䲢����������У�����ʹ�ñ����ڴ档��ˣ�Ĭ������£�Ԫ�ռ�Ĵ�С���ܱ����ڴ����ƣ�������ͨ�����²�����ָ��Ԫ�ռ�Ĵ�С��
    //Ԫ�ռ�����-XX:MetaspaceSize=8m -XX:MaxMetaspaceSize=80m
    //Ŀǰ�޷�����metaSpaceOOM......�������ַ����Ѿ�ʧЧ
    
    public void  metaSpaceOOM() {
    	int i = 0;
    	//dynamic class comes mostly from proxy pattern
        List<Object> proxys = new ArrayList<>();
        try {  

            List<ClassLoader> classLoaders = new ArrayList<ClassLoader>();  
            while (true) {
                People member = new Member();
                myInvocationHandler handler = new myInvocationHandler(member);
                proxys.add(Proxy.newProxyInstance(handler.getClass().getClassLoader(), member.getClass().getInterfaces(), handler));
            }
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    	
    public static void main(String[] args) {
    	Overflow of = new Overflow();
    	//of.heapOOM();
    	//of.stackLeak();
    	of.stackLeakByThread();
        //of.metaSpaceOOM();
	}

}
