package bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import aop.AnnotationAop;
import aop.AopTest;

@Component
public class BeanTest {
	@Autowired
	AnnotationAop annotationAop;

	@AopTest
	public void print() {
		System.out.println("BeanTest.print()");
	}
}
