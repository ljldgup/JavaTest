package DesignPatterns.IteratorAndCompositePatterns;

import java.util.Iterator;

public class MemberIterator implements Iterator{
	
	String members[];
	int pos;
	
	public MemberIterator(String[] members) {
		// TODO Auto-generated constructor stub
		this.members = members;
		this.pos = 0;
	}
	
	public void info(){
		
	}

	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		if(pos < members.length && members[pos-1] != null) return true;
		
		return false;
	}

	@Override
	public Object next() {
		// TODO Auto-generated method stub
		if (hasNext()) {
			pos++;
			return members[pos-1];
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		String[] Joke = {"cindy","marvin"};
		MemberIterator jojo = new MemberIterator(Joke);
		while(jojo.hasNext()) {
			System.out.println((String)jojo.next());
		}
	}
}
