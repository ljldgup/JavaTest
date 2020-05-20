package remind;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

//@SpringBootApplication(scanBasePackages={"remind.model.*"})
//only controller under same package will be loaded

//@SpringBootApplication
public class Application{

	//extends SpringBootServletInitializer for it to deploy in tomcat
	/*remember to put Remind at first in url when visit tomcat
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }*/
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
