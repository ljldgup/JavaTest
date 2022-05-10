package jvm;

import java.util.ArrayList;
import java.util.List;


public class Overflow {

    private int stackLength = 1;
    private int threadLength = 1;

    //VM arguments -Xmx50m -Xms50m -Xmn20m
    //heap out of memory
    public  void heapOOM() {
        List<OOMObject> list = new ArrayList<>();
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
            Thread thread = new Thread(() -> heapOOM());
            thread.start();
            threadLength++;
            if(threadLength%500 == 0) System.out.println(threadLength + " thread numbers");
        }
    }


    public static void main(String[] args) {
        Overflow of = new Overflow();
//        of.heapOOM();
        //of.stackLeak();
        of.stackLeakByThread();

    }

}
