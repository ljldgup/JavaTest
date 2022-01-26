package bean;

import entity.Account1;
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

	@AopTest(ELExpression = "#account1.name + ':' + #id")
	public void print(Account1 account1, int id) {
		log.info("BeanTest.print()");
	}
}
