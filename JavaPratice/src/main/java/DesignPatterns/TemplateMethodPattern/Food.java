package DesignPatterns.TemplateMethodPattern;

public class Food {
	public void heating() {
		System.out.println("heating");
	}
	
	//hook ¹³×ÓÊµÑé
	public void Preparation() {
		System.out.println("Do nothing");
	}
	
	public void Cook() {
		Preparation();
		heating();
	}
}

