package bean;

import entity.Account1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//注意第一种生成的不是rest接口
//@Controller
@RestController
public class GreetingController {

    @Autowired
    BeanTest beanTest;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="defaultValue") String name) {
        return String.format("function name aop test name=%s!", name);
    }


    @GetMapping("/hello")
    public String hello(@RequestParam(name="name", required=false, defaultValue="defaultValue") String name) {
        Account1 account1 = new Account1();
        account1.setId(1L);
        account1.setName(name);
        beanTest.print(account1, 999);
        return String.format("annotation aop test name=%s!", name);
    }

}
