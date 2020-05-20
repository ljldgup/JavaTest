package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication

//����xml����
@ImportResource("classpath:aop.xml")
@RestController
public class Application {

	//���controller����д���κεط�
	@RequestMapping("/cheers")
	String cheers(@RequestParam(name="name", required=false, defaultValue="World") String name) {
		return String.format("cheeeeeers %s!", name);
	}
	
    public static void main(String[] args) {
    	
        SpringApplication.run(Application.class, args);
    }

}
