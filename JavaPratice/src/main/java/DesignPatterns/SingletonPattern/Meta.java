package DesignPatterns.SingletonPattern;

public class Meta {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		singleton0 me = singleton0.getInstance();
		singleton0 you = singleton0.getInstance();
		System.out.println(me == you);

		Singleton1 s1 = Singleton1.INSTANCE;
		s1.whateverMethod();

		Singleton2 me1 = Singleton2.getInstance();
		Singleton2 you1 = Singleton2.getInstance();
		System.out.println(me1 == you1);


	}

}
