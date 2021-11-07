package ljl;

import java.util.Date;

import javax.annotation.Resource;

import ljl.DemoService;
import ljl.service.FactoriesTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

@SpringBootApplication  
public class Application implements ApplicationRunner{  

	@Resource
    private DemoService demoService;

	@Autowired
    private ConditionTest conditionTest;

	//@Resource和@Autowired都是做bean的注入时使用
	//@Resource按名称导入
	@Resource(name = "factoriesTestImpl2")
    private FactoriesTest factoriesTest;

	@Autowired
	ApplicationContext applicationContext;

	
    public static void main(String[] args) {  
        new SpringApplicationBuilder(Application.class)  
            .web(WebApplicationType.NONE) // .REACTIVE, .SERVLET
            .run(args);  
    }  
  
    @Override  
    public void run(ApplicationArguments args) throws Exception {  
        while(true) {
			System.out.println(applicationContext.getParentBeanFactory());
            factoriesTest.print();
        	demoService.test();
            System.out.println("now is " + new Date().toLocaleString());  
            Thread.sleep(1000);  
        }  
    }
      
} 