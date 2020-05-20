package DesignPatterns.StatePattern;

public class ticket {
	private in inState;
	private out outState;
	private state currentState;
	
	public state getInState() {
		return inState;
	}
	
	public state getOutState() {
		return outState;
	}
	
	public void setState(state newState) {
		this.currentState = newState;
	}
	
	public void checkIn() {
		currentState.checkIn();
	}
	
	public void checkOut() {
		currentState.checkOut();
	}
	
	public ticket() {
		// TODO Auto-generated constructor stub
		inState = new in(this);
		outState = new out(this);
		currentState = outState;
	}
	
	public static void main(String[] args) {
		
		ticket myTicket = new ticket();
		myTicket.checkIn();
		myTicket.checkIn();
		myTicket.checkOut();
		myTicket.checkOut();
		myTicket.checkIn();
		
	}
	
}
