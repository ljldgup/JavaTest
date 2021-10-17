package FunctionalProgram;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//"abc" ->[a,b,c] 扁平化，变成多个元素
public class FlatMap {
	public static void main(String[] args) {
        String[] words = new String[]{"Hello","World"};
        List<String> a = Arrays.stream(words)
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());
        a.forEach(System.out::print);
	}
}
