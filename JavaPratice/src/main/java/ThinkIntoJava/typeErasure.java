package ThinkIntoJava;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class typeErasure {
	
	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        ArrayList<String> arrayList1=new ArrayList<String>();  
        arrayList1.add("abc");  
        ArrayList<Integer> arrayList2=new ArrayList<Integer>();  
        arrayList2.add(123);  
        //String �� Integer��Ϣ������
        System.out.println(arrayList1.getClass()==arrayList2.getClass());  
        
        System.out.println("--------------------------------------------");
        
        ArrayList<Integer> arrayList3=new ArrayList<Integer>();  
        arrayList3.add(1);//��������add����ֻ�ܴ洢���Σ���Ϊ�������͵�ʵ��ΪInteger
        //���÷������add������ʱ��ȴ���Դ洢�ַ�����
        //˵����Integer����ʵ���ڱ���֮�󱻲����ˣ�ֻ������ԭʼ����Object��
        arrayList3.getClass().getMethod("add", Object.class).invoke(arrayList3, "asd");  
        for (int i=0;i<arrayList3.size();i++) {  
            System.out.println(arrayList3.get(i));  
        }  
        
        //������ͱ������޶�����ôԭʼ���;��õ�һ���߽�����ͱ������滻��
        //public class Pair<T extends Serializable&Comparable> ����ôԭʼ���;���Serializable�滻
        
        System.out.println("--------------------------------------------");
        //ָ�����͵�ʱ��Ҳ��ֻ࣬�������ʱ��ķ�������ΪObject
        //����ArrayList�У������ָ�����ͣ���ô���ArrayList�п��Է��������͵Ķ���
        //ע�⼰ʱǿ��ת�ͺ���Ȼ���Եõ���String�����Է��뷺�������󣬲��ᶪʧ��Ϣ
        Object o = new String("sdf");
        System.out.println("Object o = new String(\"sdf\"); o type " + o.getClass().getName());
	}
	
}
