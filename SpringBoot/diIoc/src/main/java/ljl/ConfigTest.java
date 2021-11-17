package ljl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import scan.ImportTest;
import scan.ScanTest;

//ComponentScan并不是要和configuration一起才能生效，导入配置，
//ImportResource，PropertySource应该也不需要

@Component
@ComponentScan(basePackages = "scan")
public class ConfigTest {
	@Autowired
	ScanTest scanTest;

	//通过别的地方@Import(ImportTest.class)引入，importTest本省没有加注解
	@Autowired
	ImportTest importTest;

	void print(){
		scanTest.print();
		importTest.print();
	}
}
