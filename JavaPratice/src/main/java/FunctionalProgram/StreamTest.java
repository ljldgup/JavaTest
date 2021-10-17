package FunctionalProgram;

import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

public class StreamTest {
	public static void main(String[] args) {
		
		String testText = "You don't really know how happy or sad something is going to make you in the future, even though you think you do right now. Put another way, psychologists know that most people are very bad at being able to predict how they will respond to positive or negative events in the future";
		
		//使用Arrays.asList返回数组
		List<String> testList = Arrays.asList(testText.split(" "));
		TreeMap<String, Integer> result1 = new TreeMap<String, Integer>();
		TreeMap<Integer, String> result2 = new TreeMap<Integer, String>();
		TreeMap<String, Integer> result3 = new TreeMap<String, Integer>();
		
		//for(String str:testList) System.out.println(str);
		
		//注意这里要用forEach 遍历，而不是map，map是映射，要有返回值
		//并且map似乎是惰性求值，单独用不会直接生效，result最后是空的
		// getOrDefault 在没有的情况下返回默认值
		testList.stream()
				.forEach(str -> result1.put(str, result1.getOrDefault(str, 0) + 1));
		
		result1.keySet().stream()
						.forEach(key -> result2.put(result1.get(key), result2.getOrDefault(result1.get(key), "") + key + " " ));
		
		//此处可以不用map，仅为实验
		result2.keySet().stream()
											//三目运算区分复数
						.map(key -> key + (key>1?" times:":" time:") + result2.get(key))
						.forEach(key -> System.out.println(key));
		
		//flatMap
		testList.stream()
		//flapMap之前先用map映射，map可以返回类型不一样，实际上此步可以和下面合成一步
		//放入函数Arrays::stream采用这样的写法，是一个静态函数
				.map(word -> word.split(""))
				//Arrays::stream 相当于调用stream 即 arr -> arr.stream()
				.flatMap(Arrays::stream)
				.filter(character -> character.matches("[a-zA-Z]"))
				.forEach(character -> result3.put(character, result3.getOrDefault(character, 0) + 1));
		
		result3.keySet().stream()
						.map(key -> key + " " + result3.get(key))
						.forEach(x -> System.out.println(x));
	}
	
} 
