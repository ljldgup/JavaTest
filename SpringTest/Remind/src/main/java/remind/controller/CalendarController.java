package remind.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CalendarController {

	//此处如果模板位于\src\main\resources\templates下，需要在build文件中包含spring-boot-devtools包
	//spring-boot-devtools 同时可以实现热部署，修改后及时生效
    @GetMapping("/calendar")
    public String calendar() {
        return "calendar";
    }
    
}
