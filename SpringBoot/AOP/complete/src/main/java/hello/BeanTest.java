package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import other.AnnotationAop;
import other.AopTest;

@Component
public class BeanTest {
	@Autowired
	AnnotationAop annotationAop;

	@AopTest
	public void print() {
		System.out.println("BeanTest.print()");
	}
}
