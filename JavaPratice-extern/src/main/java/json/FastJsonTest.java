package json;

import com.alibaba.fastjson.JSONObject;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FastJsonTest {
    public static void main(String[] args) {
        Map<String, List<Integer>> testMap = new LinkedHashMap<>();
        List<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(312);
        testMap.put("test", list);
        String json = JSONObject.toJSONString(testMap);
        System.out.println(json);
        Map jsonMap = (Map) JSONObject.parse(json);
        System.out.println(jsonMap);

        int[][] a = {{1, 2, 3, 4}, {1, 2, 3}};
        json = JSONObject.toJSONString(a);
        System.out.println(json);
        Object jsonObject = JSONObject.parse(json);
        System.out.println((int[][]) jsonObject);
    }
}
