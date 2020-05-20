package DesignPatterns.ObserverPattern;
import java.util.Observable;
import java.util.Observer;

public class ObserverB implements Observer {

	public void subscribe(Observable sub) {
		sub.addObserver(this);
	}
	
	@Override
	public void update(Observable sub, Object arg1) {
		// TODO Auto-generated method stub
		
		if (sub instanceof SubjectA) {
			SubjectA SubA = (SubjectA) sub;
			System.out.println("get in ObserverB: " + SubA.p1*SubA.p1);
		}
	}

}
