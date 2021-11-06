package LJL;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication  
public class Application implements ApplicationRunner{  
    //@Resource和@Autowired都是做bean的注入时使用
    //其实@Resource并不是Spring的注解，它的包是javax.annotation.Resource，需要导入，但是Spring支持该注解的注入。
	@Resource
    private DemoService demoService;
	
    public static void main(String[] args) {  
        new SpringApplicationBuilder(Application.class)  
            .web(WebApplicationType.NONE) // .REACTIVE, .SERVLET  

            .run(args);  
    }  
  
    @Override  
    public void run(ApplicationArguments args) throws Exception {  
        while(true) {  
        	demoService.test();
            System.out.println("now is " + new Date().toLocaleString());  
            Thread.sleep(1000);  
        }  
    }


      
} 