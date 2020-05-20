package DesignPatterns.FactoryPattern;

public class ProcessorFactory {
	public static Processor createProcessor(String type) {
		
		switch (type) {
		case "Apple":
			return new AType();
		case "HuaWei":
			return new Qualcomm();
		default:
			break;
		}
		
		return null;
	}
}
