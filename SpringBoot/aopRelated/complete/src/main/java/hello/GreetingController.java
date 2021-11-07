package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import other.AopTest;

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
        beanTest.print();
        return String.format("annotation aop test name=%s!", name);
    }

}
