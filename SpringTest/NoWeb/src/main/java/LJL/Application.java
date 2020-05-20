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