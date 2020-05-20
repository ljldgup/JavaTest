package DesignPatterns.IteratorAndCompositePatterns;

import java.util.ArrayList;

public class Family extends Group {
	private ArrayList<String> members = new ArrayList<String>();
	private String name;
	public Family(String name) {
		// TODO Auto-generated constructor stub
		this.name = name;  
	}
	
	public void addMember(String member) {
		// TODO Auto-generated method stub
		members.add(member);
	}
	
	@Override
	public void printInfo() {
		// TODO Auto-generated method stub
		format();
		System.out.println("Family " + name + ":");
		for (String member : members) {
			format();
			System.out.println(member);
		}
		
	}

}
