package language;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TypeNullTest {

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static  class Test{
        public Short a = null;
    }

    final static short b = 1;
    public static void main(String[] args) {
        List<Test> testList = new ArrayList<>();
        testList.add(new Test((short) 1));
        testList.add(new Test());
        //包装类型和基础类型比较会转为基础类型，null转过去会报NullPointerException
        //testList.stream().filter(t->t.getA() == b).collect(Collectors.toList());

        //filter内函数返回true保留
        System.out.println(testList.stream().filter(x->true).count());

        Double d1 = 1.0;
        Double d2 = null;
        //包装类型为null相加会报错
        System.out.println(d1 + d2);
    }
}
