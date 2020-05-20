package FunctionalProgram;


import java.util.function.Function;

public class ComposeAndThen {
    public static void main(String[] args) {
        Function<Integer, Integer> times2 = i -> i * 2; //�ӱ�����
        Function<Integer, Integer> squared = i -> i * i; //ƽ������

        System.out.println(times2.apply(4)); //8
        System.out.println(squared.apply(4)); //16

        System.out.println(times2.compose(squared).apply(4));  //32   times2(squared(4))
        System.out.println(times2.andThen(squared).apply(4));  //64   squared(times2(4))

        //identity ��������
        System.out.println(Function.identity().compose(squared).apply(4));   //4 (squared(4))
    }
}
