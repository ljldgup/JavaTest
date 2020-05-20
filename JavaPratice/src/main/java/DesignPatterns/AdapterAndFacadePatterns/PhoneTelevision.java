package DesignPatterns.AdapterAndFacadePatterns;

public class PhoneTelevision implements Television{
	private Phone phone;
	public PhoneTelevision(Phone phone) {
		this.phone = phone;
	}
	public void play() {
		if(phone != null) {
			phone.play();
			System.out.println("project to television");
			System.out.println("Play on television");
		}
	}
	
	public static void main(String[] args) {
		new PhoneTelevision(new Phone()).play();	
	}
}
