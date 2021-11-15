package ljl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import scan.ScanTest;

//ComponentScan并不是要和configuration一起才能生效，导入配置，
//ImportResource，PropertySource应该也不需要

@Component
@ComponentScan(basePackages = "scan")
public class Config {
	@Autowired
	ScanTest scanTest;

	void print(){
		scanTest.print();
	}
}
