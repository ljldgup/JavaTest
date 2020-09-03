package FunctionalProgram;

import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

public class StreamTest {
	public static void main(String[] args) {
		
		String testText = "You don't really know how happy or sad something is going to make you in the future, even though you think you do right now. Put another way, psychologists know that most people are very bad at being able to predict how they will respond to positive or negative events in the future";
		
		//ʹ��Arrays.asList��������
		List<String> testList = Arrays.asList(testText.split(" "));
		TreeMap<String, Integer> result1 = new TreeMap<String, Integer>();
		TreeMap<Integer, String> result2 = new TreeMap<Integer, String>();
		TreeMap<String, Integer> result3 = new TreeMap<String, Integer>();
		
		//for(String str:testList) System.out.println(str);
		
		//ע������Ҫ��forEach ������������map��map��ӳ�䣬Ҫ�з���ֵ
		//����map�ƺ��Ƕ�����ֵ�������ò���ֱ����Ч��result����ǿյ�
		// getOrDefault ��û�е�����·���Ĭ��ֵ
		testList.stream()
				.forEach(str -> result1.put(str, result1.getOrDefault(str, 0) + 1));
		
		result1.keySet().stream()
						.forEach(key -> result2.put(result1.get(key), result2.getOrDefault(result1.get(key), "") + key + " " ));
		
		//�˴����Բ���map����Ϊʵ��
		result2.keySet().stream()
											//��Ŀ�������ָ���
						.map(key -> key + (key>1?" times:":" time:") + result2.get(key))
						.forEach(key -> System.out.println(key));
		
		//flatMap
		testList.stream()
		//flapMap֮ǰ����mapӳ�䣬map���Է������Ͳ�һ����ʵ���ϴ˲����Ժ�����ϳ�һ��
		//���뺯��Arrays::stream����������д������һ����̬����
				.map(word -> word.split(""))
				//Arrays::stream �൱�ڵ���stream �� arr -> arr.stream()
				.flatMap(Arrays::stream)
				.filter(character -> character.matches("[a-zA-Z]"))
				.forEach(character -> result3.put(character, result3.getOrDefault(character, 0) + 1));
		
		result3.keySet().stream()
						.map(key -> key + " " + result3.get(key))
						.forEach(x -> System.out.println(x));
	}
	
} 
