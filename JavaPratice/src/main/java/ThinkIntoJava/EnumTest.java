package ThinkIntoJava;

public class EnumTest {
	enum test1{
		AFK{
			public String info(){
				return "AFK.info()";
			}
		},
		FFF{
			public String info(){
				return "FFF.info()";
			}
		},
			
		FCCC{
			public String info(){
				return "FCCC.info()";
			}
		};
	
		public String info() {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	public static void main(String[] args) {
		for(test1 t1:test1.values()) {
			System.out.println(t1.name() + "," + t1.ordinal() + "," + t1.info());
		}
	}
}
