package hello;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.concurrent.TimeUnit;
import java.util.Random;

@Component
@Controller
public class GreetingController {
    
    Random random = new Random();

    @Value("${test.name1}")
    String name1;
    
    @Value("${test.name2}")
    String name2;
    
    @Value("${test.operation}")
    String operation;

    @Value("${test.times}")
    int times;

    @Value("${server.port}")
    int port;
   

    @Value("${test.blockTime}")
    int blockTime;
    
    @GetMapping("/")
    @ResponseBody public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name) throws InterruptedException{
        //超过2000ms，被熔断
        TimeUnit.MILLISECONDS.sleep(random.nextInt()%blockTime);
        return String.format("from %d, %s %s %s %s %d times",port,  name, name1, operation, name2, times);
    }

}
