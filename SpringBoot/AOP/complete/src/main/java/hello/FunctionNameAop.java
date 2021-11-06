package hello;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

//Component将当前的类注册为bean， Bean则是当前函数返回的对象作为Bean
@Component
@Aspect
public class FunctionNameAop
{
    @Pointcut("execution(* *.greeting(..))")
    public void greeting(){}
    
    @Around("greeting()")
    public void printTime(ProceedingJoinPoint pjp) throws Throwable
    {
        System.out.println("function name aop greeting运行前:" + System.currentTimeMillis());
        pjp.proceed();
        System.out.println("function name aop greeting运行后:" + System.currentTimeMillis());
    }
}