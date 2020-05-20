package FunctionalProgram;

import java.util.stream.IntStream;
import java.lang.Math;

//����������
public class NumberClassifier {
	public static IntStream factorOf(int number) {
		// IntStream���кܶ������÷�
		//Math.sqrt��С������
		return IntStream.range(1, (int) (Math.sqrt(number) + 1))
										.filter(x -> number % x == 0);
	}
	
	public static IntStream AllfactorOf(int number) {
		// IntStream���кܶ������÷�
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
		//���ַ�Χ����.parallel()�����Ե��������ã�����˳��ᱻ����
		IntStream.range(1, 10900090).parallel()
								.filter(num -> isPerfect(num))
								.forEach(perfectNum -> {
									System.out.print(perfectNum + ":");
									//������ԭ�������⴦����������дһ����ȫ�����ӵĺ���������ʽ��̲�������̣�����
									AllfactorOf(perfectNum).forEach(factor -> System.out.print(" " + factor));
									System.out.println();
								});
	}
}
