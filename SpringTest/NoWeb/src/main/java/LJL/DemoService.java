package LJL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
//@Service用于标注业务层组件，
//@Controller用于标注控制层组件（如struts中的action）,
//@Repository用于标注数据访问组件，即DAO组件，
//@Component泛指组件，当组件不好归类的时候，我们可以使用这个注解进行标注。  
@Service
public class DemoService {

	@Value("normal")
    private String normal; // 注入普通字符串
    
    @Value("\"#{systemProperties['os.name']}\"")
    private String testValueAnno1;
    
    // 这里的值来自resources/application.properties，spring boot启动时默认加载此文件
    @Value("${app.name}")
    private String testValueAnno2;
    
    public void test(){
        System.out.println(normal);
        System.out.println(testValueAnno1);
        System.out.println(testValueAnno2);
    }
}