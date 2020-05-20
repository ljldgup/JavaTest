package ThinkIntoJava;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class typeErasure {
	
	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        ArrayList<String> arrayList1=new ArrayList<String>();  
        arrayList1.add("abc");  
        ArrayList<Integer> arrayList2=new ArrayList<Integer>();  
        arrayList2.add(123);  
        //String 和 Integer信息被擦除
        System.out.println(arrayList1.getClass()==arrayList2.getClass());  
        
        System.out.println("--------------------------------------------");
        
        ArrayList<Integer> arrayList3=new ArrayList<Integer>();  
        arrayList3.add(1);//这样调用add方法只能存储整形，因为泛型类型的实例为Integer
        //利用反射调用add方法的时候，却可以存储字符串。
        //说明了Integer泛型实例在编译之后被擦除了，只保留了原始类型Object。
        arrayList3.getClass().getMethod("add", Object.class).invoke(arrayList3, "asd");  
        for (int i=0;i<arrayList3.size();i++) {  
            System.out.println(arrayList3.get(i));  
        }  
        
        //如果类型变量有限定，那么原始类型就用第一个边界的类型变量来替换。
        //public class Pair<T extends Serializable&Comparable> ，那么原始类型就用Serializable替换
        
        System.out.println("--------------------------------------------");
        //指定泛型的时候，也差不多，只不过这个时候的泛型类型为Object
        //比如ArrayList中，如果不指定泛型，那么这个ArrayList中可以放任意类型的对象。
        //注意及时强制转型后，仍然可以得到是String，所以放入泛型容器后，不会丢失信息
        Object o = new String("sdf");
        System.out.println("Object o = new String(\"sdf\"); o type " + o.getClass().getName());
	}
	
}
