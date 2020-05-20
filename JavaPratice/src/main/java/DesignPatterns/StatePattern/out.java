package DesignPatterns.StatePattern;

public class out implements state {
	ticket t;
	public out(ticket t) {
		// TODO Auto-generated constructor stub
		this.t = t;
	}
	
	@Override
	public void checkIn() {
		// TODO Auto-generated method stub
		System.out.println("checkin, welcome");
		t.setState(t.getInState());
	}

	@Override
	public void checkOut() {
		// TODO Auto-generated method stub
		System.out.println("Error,already out!");
	}

}
