package FunctionalProgram;

import java.util.stream.IntStream;
import java.lang.Math;

//完美数计算
public class NumberClassifier {
	public static IntStream factorOf(int number) {
		// IntStream还有很多其他用法
		//Math.sqrt减小计算量
		return IntStream.range(1, (int) (Math.sqrt(number) + 1))
										.filter(x -> number % x == 0);
	}
	
	public static IntStream AllfactorOf(int number) {
		// IntStream还有很多其他用法
		return IntStream.range(1, number)
										.filter(x -> number % x == 0);
	}
	public static int aliquotSum(int number) {
		//factorOf(number).forEach( x -> System.out.print(" " + x));
		//System.out.print(" " + (factorOf(number).map(x -> x + number/x).sum()-number));
		//System.out.println();
		return factorOf(number).map(x -> x + number/x).sum() - number;
	}
	
	public static boolean isPerfect(int number) {
		return aliquotSum(number) == number;
	}
	
	public static boolean isAboundant(int number) {
		return aliquotSum(number) > number;
	}
	
	public static boolean isDeficient(int number) {
		return aliquotSum(number) < number;
	}
	
	public static void main(String[] args) {
		//数字范围打了.parallel()有明显的提升作用，但是顺序会被打乱
		IntStream.range(1, 10900090).parallel()
								.filter(num -> isPerfect(num))
								.forEach(perfectNum -> {
									System.out.print(perfectNum + ":");
									//与其用原来的特殊处理，不如重新写一个求全部因子的函数，函数式编程不保存过程！！！
									AllfactorOf(perfectNum).forEach(factor -> System.out.print(" " + factor));
									System.out.println();
								});
	}
}
