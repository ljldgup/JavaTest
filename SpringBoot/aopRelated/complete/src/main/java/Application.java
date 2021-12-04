import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.*;

@SpringBootApplication
//导入xml配置
@ImportResource("classpath:aop.xml")
@RestController
//没有包的情况只能这样扫
@ComponentScan("aop,entity,service,bean")
@MapperScan("mapper")
@EnableAsync
@EnableScheduling
public class Application {

	//配合controller可以写在任何地方
	@RequestMapping("/cheers")
	String cheers(@RequestParam(name="name", required=false, defaultValue="defaultValue") String name) {
		return String.format("xml aop test name=%s!", name);
	}

	//如果线程池的拒绝策略设置成DiscardPolicy或者DiscardOldestPolicy，通过Future获取执行结果，可能导致线程会一直阻塞。

	@Bean
	public ThreadPoolExecutor customExecutor(){
		return new ThreadPoolExecutor(1, 2, 1000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(5),
				Executors.defaultThreadFactory(), new ThreadPoolExecutor.DiscardPolicy());
	}
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
