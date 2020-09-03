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
                // list保留引用，避免Full GC 回收 
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
 
    // 多线程方式造成栈内存溢出 OutOfMemoryError
    //每个线程需要一个栈空间，降低Xss能增加线程数量
    //运行该代码可能造成系统假死！！！！
    public void stackLeakByThread() {
        while(true) {
            Thread thread = new Thread(() -> dontStop());
            thread.start();
            threadLength++;
            if(threadLength%500 == 0) System.out.println(threadLength + " thread numbers");
        }
    }

    //方法区溢出，动态载入类，导致元空间溢出，-XX:MetaspaceSize
    //JDK 1.7 PermGen OOM, JDK 1.8 Metaspace OOM
    //因此，可以大致验证 JDK 1.7 和 1.8 将字符串常量由永久代转移到堆中，并且 JDK 1.8 中已经不存在永久代的结论。
    //类的信息移入元空间，元空间并不在虚拟机中，而是使用本地内存。因此，默认情况下，元空间的大小仅受本地内存限制，但可以通过以下参数来指定元空间的大小：
    //元空间设置-XX:MetaspaceSize=8m -XX:MaxMetaspaceSize=80m
    //目前无法重现metaSpaceOOM......可能这种方法已经失效
    
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
