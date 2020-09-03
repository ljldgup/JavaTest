package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.beans.factory.annotation.Autowired;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Value;

@RestController
public class UserApplication {

  @Autowired
  GreetingService greetingService;

  @Value("${server.port}")
  int port;
    
  //HystrixCommand需要放在这里熔断才能检测到，reliable要和hi函数参数相同，不然会fallback method wasn't found
  @RequestMapping("/hi")
  @HystrixCommand(fallbackMethod = "reliable")
  public String hi(@RequestParam(value="name", defaultValue="ribbon") String name) {
    //String greeting = this.restTemplate.getForObject("http://EUREKA-CLIENT/", String.class);
    //use 
    String greeting = greetingService.greeting(); 
    return String.format("from %d %s, %s!",port, name, greeting);
  }

  @RequestMapping("/")
  @ResponseBody  public String index() {
    return "a aaaa aaaaaaaaaa!";
  }
  
  public String reliable(String name) {
    return name + ": fallbackMethod reliable() ";
  }
}

