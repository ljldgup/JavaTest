package FunctionalProgram;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ObjectStream {
	public static void main(String[] args) {
		new Random()
		.doubles()
		.limit(10)
		.sorted()
		.forEach(System.out::println);
		
		IntStream.rangeClosed(0, 10)
		.filter(x -> x%3==0)
		.sorted()
		.forEach(System.out::println);
		
	    List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
	    System.out.println("allMatch:" + strings.stream().allMatch(x->x.startsWith("a")));
	    System.out.println("anyMatch:" + strings.stream().anyMatch(x->x.startsWith("a")));
	    System.out.println("Match:" + strings.stream().filter(x->x.startsWith("a")).count());
	    String mergedString = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.joining(", "));
	    System.out.println("ºÏ²¢×Ö·û´®: " + mergedString);
	}
}
