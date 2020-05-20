package DesignPatterns.ObserverPattern;
import java.util.Observable;
import java.util.Observer;

public class ObserverA implements Observer {
	
	public void subscribe(Observable sub) {
		sub.addObserver(this);
	}
	
	@Override
	public void update(Observable sub, Object arg1) {
		// TODO Auto-generated method stub
		System.out.println("get in ObserverA");
		if (sub instanceof SubjectA) {
			SubjectA SubA = (SubjectA) sub;
			System.out.println(SubA.p1);
		}
	}

}
