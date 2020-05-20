package DesignPatterns.SingletonPattern;

public class Lonelyness {
	//volatile is necessary, read and write atomic
	public static volatile Lonelyness me;

	private Lonelyness() {
		System.out.println("lonely guy born -.-");
	}
	
	public static Lonelyness getInstance() {
		if(me == null) {
			synchronized (Lonelyness.class) {
				//double check is necessary
				if(me == null) {
					me = new Lonelyness();
				}
			}
		}
		return me;
	}
}
