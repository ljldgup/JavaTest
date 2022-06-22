package language;

import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

public class CollectorNullTest {
    @Data
    public static class Test{
        int data;
        String name;
    }
    public static void main(String[] args) {
        List<Test> dataList = new ArrayList<>();

        //可以插入null，会导致后面Collectors.toMap报异常
        dataList.add(null);


        Map<String, Object> testStringMap = new HashMap<>();
        //k,v都可以为null,取值也可以
        testStringMap.put(null, null);
        testStringMap.get((String) null);
        testStringMap.get(null);

        Map<Integer, Object> testIntMap = new HashMap<>();
        //k,v都可以为null,取值也可以
        testIntMap.put(null, null);
        testIntMap.get((String) null);
        testIntMap.get(null);

        //Collectors.toMap生成map，value不能有空值
        //dataList.stream().collect(Collectors.toMap(Test::getName, Test::getData));
        //Map<String, Integer> dataMap = dataList.stream().collect(Collectors.toMap(Test::getName, Test::getData));
        //System.out.println(dataMap.size());

        //直接报错
//        Arrays.asList(null);

        String[] array = {"dsaf","sdf"};

        //new String[0]貌似只是指明类型
        System.out.println(Arrays.deepToString(Arrays.asList(array).toArray(new String[0])));
        String[] array1 = {};

        //空数组可以join
        System.out.println(Arrays.stream(array1).collect(Collectors.joining(",")));
    }
}
