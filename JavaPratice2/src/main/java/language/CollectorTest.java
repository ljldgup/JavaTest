package language;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

public class CollectorTest {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        //collectingAndThen先collecting,然后将collect的结果交给后面一个函数处理
        //这里先求元素两倍和的平均数，然后输出其平方
        Double result = list.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.averagingLong(v -> {
                            System.out.println("v--" + v + "--> " + v * 2);
                            return v * 2;
                        }),
                        s -> {
                            System.out.println("s--" + s + "--> " + s * s);
                            return s * s;
                        }));
        System.out.println(result);
        System.out.println();

        result = list.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.summingDouble(v -> {
                            System.out.println("v--" + v + "--> " + v * 2);
                            return v * 2;
                        }),
                        s -> {
                            System.out.println("s--" + s + "--> " + s * s);
                            return s * s;
                        }));
        System.out.println(result);

        IntSummaryStatistics intSummaryStatistics = list.stream().collect(Collectors.summarizingInt(i -> i));
        System.out.println("平均值: " + intSummaryStatistics.getAverage());
        System.out.println("统计个数: " + intSummaryStatistics.getCount());
        System.out.println("最大值: " + intSummaryStatistics.getMax());
        System.out.println("最小值: " + intSummaryStatistics.getMin());
        System.out.println("总和: " + intSummaryStatistics.getSum());
    }
}
