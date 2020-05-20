package DesignPatterns.TemplateMethodPattern;

public class Vegetables extends Food {
	@Override
	public void Preparation() {
		// TODO Auto-generated method stub
		//super.Preparation();
		System.out.println("Add water, add condiment" + 
				"");
	}
	public static void main(String[] args) {
		Food patota = new Vegetables();
		Food rice = new Rice();
		patota.Cook();
		rice.Cook();
	}
}
