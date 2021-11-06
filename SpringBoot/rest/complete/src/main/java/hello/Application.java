package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
//导入xml配置
@ImportResource("classpath:aop.xml")
//springboot 默认扫Application类,other 和hello平级不能被扫，所以要用ComponentScan加上
@ComponentScan("other, hello")
@RestController
public class Application {

	//配合controller可以写在任何地方
	@RequestMapping("/cheers")
	String cheers(@RequestParam(name="name", required=false, defaultValue="defaultValue") String name) {
		return String.format("xml aop test name=%s!", name);
	}
	
    public static void main(String[] args) {
    	
        SpringApplication.run(Application.class, args);
    }

}
