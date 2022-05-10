package language;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.SortedMap;
import java.util.function.Function;

public class OptionalTest {
    public static void main(String[] args) {
        String test[]={"sdf","fge","sdf","sdffegeg","fsdf"};
        String result;
        //不存在的时候直接报错
        //result = Arrays.stream(test).filter(a->a.startsWith("b")).findFirst().get();
        result = Arrays.stream(test).filter(a->a.startsWith("f")).findFirst().orElseGet(()->null);
        result = Arrays.stream(test).filter(a->a.startsWith("f")).findFirst().orElse(null);
        System.out.println(result);

        Optional<Date> eventCreateTime = Optional.empty();
        System.out.println(eventCreateTime.orElse(new Date()));
        System.out.println(eventCreateTime.orElseGet(Date::new));
    }
}
