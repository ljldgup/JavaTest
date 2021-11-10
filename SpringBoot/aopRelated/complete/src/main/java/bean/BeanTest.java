package bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import aop.AnnotationAop;
import aop.AopTest;

@Slf4j
@Component
public class BeanTest {
	@Autowired
	AnnotationAop annotationAop;

	@AopTest
	public void print() {
		log.info("BeanTest.print()");
	}
}
