package aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AnnotationAop {

	//对注解的代理
	@Pointcut("@annotation(aop.AopTest)")
	public void aopTest(){}

	@Around("aopTest()")
	public void printTime(ProceedingJoinPoint pjp) throws Throwable
	{
		System.out.println("aopTest运行前:" + System.currentTimeMillis());
		pjp.proceed();
		System.out.println("aopTest运行后:" + System.currentTimeMillis());
	}
}
