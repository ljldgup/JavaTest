package DesignPatterns.DecoratorPattern;

public class Meat extends HotSpot{
	HotSpot hotSpot;
	public Meat(HotSpot hs) {
		super();
		// TODO Auto-generated constructor stub
		hotSpot = hs;
		component = "猪肉";
	}

	@Override
	public String getComponets() {
		// TODO Auto-generated method stub
		return hotSpot.getComponets() + "," + component;
	}
	
}
