package ThinkIntoJava;


import java.util.stream.Stream;
public class Base {
	@annotationTest1(description = "test1")
	public void test1() {
		System.out.println("invoke Base.test1");
	}
	
	@annotationTest1(id = 1313)
	public void test2() {
		System.out.println("invoke Base.test2");
	}
	
	@annotationTest1()
	public void test3() {
		System.out.println("invoke Base.test3");
	}
	
	public static void main(String[] args) {
			Base b = new Base();
			Stream.of(Base.class.getMethods())
				.map(m -> {
					try {
						m.invoke(b);
					} catch (Exception e) {
						// TODO Auto-generated catch block
					}
					return m.getAnnotation(annotationTest1.class);
					})
				.filter(a -> a != null)
				.forEach(a -> System.out.println(a.id() + " " + a.description()));
	}
}
