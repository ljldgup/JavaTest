package remind.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CalendarController {

	//�˴����ģ��λ��\src\main\resources\templates�£���Ҫ��build�ļ��а���spring-boot-devtools��
	//spring-boot-devtools ͬʱ����ʵ���Ȳ����޸ĺ�ʱ��Ч
    @GetMapping("/calendar")
    public String calendar() {
        return "calendar";
    }
    
}
