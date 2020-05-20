package DesignPatterns.IteratorAndCompositePatterns;

import java.util.ArrayList;

public abstract class Group {

	protected static int blanks;
	
	//������ʾ
	public void format() {
		int i = 0;
		while(i<blanks) {
			System.out.print("	");
			i++;
		}
	}
	
	public abstract void printInfo();
}
