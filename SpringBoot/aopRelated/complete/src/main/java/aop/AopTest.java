package aop;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD}) //注解应用类型(应用到方法的注解)
@Retention(RetentionPolicy.RUNTIME) // 注解的类型
public @interface AopTest {
	String ELExpression() default "";
}
