package ljl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@PropertySource("condition.properties")
//@ConditionalOnExpression(" '${app.name}'.equals('open')")
//这里表达式不成立将导致ConditionTest的bean没有生成，condition.properties没有载入，使得整个程序都失败
@ConditionalOnExpression("${test.value} == 1")
public class ConditionTest {
	public static DemoService demoService;


	@Value("condition.1")
	String var1;

	@Autowired
	public void setDemoService(DemoService demoService){
		ConditionTest.demoService = demoService;
	}
}
