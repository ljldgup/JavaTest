package hello;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

//Component����ǰ����ע��Ϊbean�� Bean���ǵ�ǰ�������صĶ�����ΪBean
@Component
@Aspect
public class TimeHandler
{
    @Pointcut("execution(* *.greeting(..))")
    public void greeting(){}
    
    @Around("greeting()")
    public void printTime(ProceedingJoinPoint pjp) throws Throwable
    {
        System.out.println("greeting����ǰ:" + System.currentTimeMillis());
        pjp.proceed();
        System.out.println("greeting���к�:" + System.currentTimeMillis());
    }
}