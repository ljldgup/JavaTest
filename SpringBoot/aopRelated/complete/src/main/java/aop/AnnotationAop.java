package aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


@Slf4j
@Component
@Aspect
public class AnnotationAop {

	//对注解的代理
	@Pointcut("@annotation(aop.AopTest)")
	public void aopTest(){}

	@Around("aopTest()")
	public void printTime(ProceedingJoinPoint joinPoint) throws Throwable
	{
		log.info("aopTest运行前:" + System.currentTimeMillis());

		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		signature.getParameterNames();
		String[] parameterNames = signature.getParameterNames();
		Object [] args = joinPoint.getArgs();
		AopTest test = method.getAnnotation(AopTest.class);
		String result = parseExpression(test.ELExpression(), parameterNames, args);
		System.out.println("EL表达式解析结果:"+result);
		joinPoint.proceed();
		log.info("aopTest运行后:" + System.currentTimeMillis());
	}

	private String parseExpression(String expressionString, String[] parameterNames, Object[] args) {
		ExpressionParser parser = new SpelExpressionParser();
		StandardEvaluationContext context = new StandardEvaluationContext();
		for (int i = 0; i < parameterNames.length; i++) {
			context.setVariable(parameterNames[i], args[i]);
		}
		String result = parser.parseExpression(expressionString).getValue(context, String.class);
		return result;
	}
}
