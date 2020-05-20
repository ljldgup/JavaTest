package DesignPatterns.ObserverPattern;

import java.util.Observable;
public class SubjectA extends Observable{
	int p1;
	
	public SubjectA(int p1) {
		super();
		this.p1 = p1;
	}
	
	public void publish(int p) {
		p1=p;
		super.setChanged();
		notifyObservers();
	}
	
	
	public static void main(String[] args) {
		SubjectA SA = new SubjectA(1);
		ObserverA OA = new ObserverA();
		ObserverB OB = new ObserverB();
		
		OA.subscribe(SA);
		OB.subscribe(SA);
		
		SA.publish(3);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SA.publish(6);
	}
}
