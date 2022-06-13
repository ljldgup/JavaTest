package FunctionalProgram;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class BiConsumerTest {


    public static void main(String[] args) {
        Map<String, Integer> contacts = new HashMap() ;

        contacts.put("John", 123456);
        contacts.put("Bill", 12580);
        contacts.put("Lisa", 16979);
        //System.out.println(contacts);
        BiConsumer<String, Integer> biConsumer1 =
                (name, phone) -> System.out.println("Name: " + name + " Number: " + phone);
        BiConsumer<String, Integer> biConsumer2 =
                (name, phone) -> System.out.println("Name: " + name.toUpperCase() + " Number: " + phone);

        //andThen是将两个consumer拼接
        contacts.forEach(biConsumer1.andThen(biConsumer2));

        //System.out.println(contacts);

    }

}
