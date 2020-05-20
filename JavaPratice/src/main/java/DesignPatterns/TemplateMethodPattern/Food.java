package DesignPatterns.TemplateMethodPattern;

public class Food {
	public void heating() {
		System.out.println("heating");
	}
	
	//hook ����ʵ��
	public void Preparation() {
		System.out.println("Do nothing");
	}
	
	public void Cook() {
		Preparation();
		heating();
	}
}

