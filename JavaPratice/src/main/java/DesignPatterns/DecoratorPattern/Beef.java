package DesignPatterns.DecoratorPattern;

public class Beef extends HotSpot{
	HotSpot hotSpot;
	public Beef(HotSpot hs) {
		super();
		// TODO Auto-generated constructor stub
		hotSpot = hs;
		component = "ЕЈИв";
	}

	@Override
	public String getComponets() {
		// TODO Auto-generated method stub
		return hotSpot.getComponets() + "," + component;
	}
}
