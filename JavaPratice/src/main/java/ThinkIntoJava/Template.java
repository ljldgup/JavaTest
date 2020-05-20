package ThinkIntoJava;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
public class Template {
	public static <k,v> Map<k,v> map(){
		return new HashMap<k,v>();
	}
	
	public static <T> List<T> list(){
		return new ArrayList<T>();
	}
	
	public static void main(String[] args) {
		List<String> lt= list();
		lt.add("123");
		Map<Integer, String>  mt = map();
		mt.put(1, "123");
	}
}
