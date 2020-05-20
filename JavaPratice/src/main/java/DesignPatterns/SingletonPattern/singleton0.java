package DesignPatterns.SingletonPattern;

public class singleton0 {
	//volatile is necessary, read and write atomic
	public static volatile singleton0 me;

	private singleton0() {
		System.out.println("lonely guy born -.-");
	}
	
	public static singleton0 getInstance() {
		if(me == null) {
			synchronized (singleton0.class) {
				//double check is necessary
				if(me == null) {
					me = new singleton0();
				}
			}
		}
		return me;
	}
}
