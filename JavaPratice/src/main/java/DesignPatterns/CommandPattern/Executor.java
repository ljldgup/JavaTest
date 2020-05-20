package DesignPatterns.CommandPattern;

import java.util.ArrayList;
import java.util.List;

public class Executor {
	List<Command> cmdList;
	
	public Executor() {
		// TODO Auto-generated constructor stub
		cmdList = new ArrayList<>();
	}

	public Executor addCommand(Command cmd) {
		// TODO Auto-generated constructor stub
		cmdList.add(cmd);
		return this;
	}
	
	public void execute() {
		for(Command cmd:cmdList) {
			if(cmd!=null) cmd.execute();
		}
	}
	
	public static void main(String[] args) {
		new Executor().addCommand(new Insert())
					.addCommand(new Shot())
					.execute();
		
		System.out.println("------------------------------------");
		new Executor().addCommand(new Shot())
					.addCommand(new Insert())
					.execute();
	}
}

