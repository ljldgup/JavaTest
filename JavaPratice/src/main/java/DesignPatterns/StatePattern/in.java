package DesignPatterns.StatePattern;

public class in implements state {
	
	ticket t;
	
	public in(ticket t) {
		// TODO Auto-generated constructor stub
		this.t = t;
	}
	
	@Override
	public void checkIn() {
		// TODO Auto-generated method stub
		System.out.println("Error,already in!");
	}

	@Override
	public void checkOut() {
		// TODO Auto-generated method stub
		System.out.println("Checkout, goodbye!");
		t.setState(t.getOutState());
	}


}
