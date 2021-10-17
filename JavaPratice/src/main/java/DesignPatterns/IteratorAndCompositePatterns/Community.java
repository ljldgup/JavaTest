package DesignPatterns.IteratorAndCompositePatterns;

import java.util.ArrayList;

public class Community extends Group{

	private String name;
	private ArrayList<Group> groups = new ArrayList<Group>();
	
	public Community(String name) {
		this.name = name;
		// TODO Auto-generated constructor stub
	}

	
	public void addMember(Group group) {
		this.groups.add(group);
	}
	
	public void printInfo() {
		format();
		
		//每个community后面加一个tab
		blanks++;
		System.out.println("Community " + name + ":");
		for(Group group:groups) group.printInfo();
		blanks--;
	}
	
	public static void main(String[] args) {
		Community CA = new Community("A");
		Community CB = new Community("B");
		Community CC = new Community("C");
		Community CD = new Community("D");
		Family A,B,C,D;
		A=new Family("1");
		B=new Family("DMC1");
		C=new Family("DMC3");
		D=new Family("resident evil");
		A.addMember("marvin");
		A.addMember("cindy");
		B.addMember("Dante");
		B.addMember("Trash");
		C.addMember("Vergil");
		D.addMember("Lion");
		D.addMember("ada");
		CA.addMember(CB);
		CA.addMember(A);
		CB.addMember(B);
		CC.addMember(C);
		CA.addMember(D);
		CB.addMember(CC);
		CA.addMember(CD);
		CA.printInfo();
	}
}
