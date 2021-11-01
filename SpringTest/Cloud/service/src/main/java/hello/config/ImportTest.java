package hello.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.*;

@Configuration

//根据占位符加载配置
@PropertySource("classpath:env/${spring.profiles.active}/application.yml")

//ImportResource在加载各类环境之后,用来加载xml定义的bean
//@ImportResource(locations = "${test.path}")

@ComponentScan(basePackages = "hello")
public class ImportTest {

	public ImportTest(){
		System.out.println("ImportTest init");
	}

}
