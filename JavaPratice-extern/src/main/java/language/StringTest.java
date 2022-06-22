package language;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StringTest {
    public static void main(String[] args) {
        System.out.println("sdf".split(",").length);

        StringBuilder sb = new StringBuilder();
        String s = sb.toString();
        //s是""
        System.out.println(s);

//        s = "1";
//        s = s.substring(1, s.length() - 1);
        System.out.println(s);
        String[] ar = {"1", "2", null, "3"};
        System.out.println(Arrays.stream(ar).collect(Collectors.joining(";")));
    }
}
