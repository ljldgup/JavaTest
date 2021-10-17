package DesignPatterns.DecoratorPattern;

public class SourHotPot extends HotSpot{
	
	public SourHotPot() {
		// TODO Auto-generated constructor stub
		component = "酸汤";
	}

	@Override
	public String getComponets() {
		// TODO Auto-generated method stub
		return component;
	}
	
	public static void main(String[] args) {
		HotSpot shp = new SourHotPot();
		shp = new Beef(shp);
		shp = new Beef(shp);
		shp = new Meat(shp);
		System.out.println(shp.getComponets());
	}
}
