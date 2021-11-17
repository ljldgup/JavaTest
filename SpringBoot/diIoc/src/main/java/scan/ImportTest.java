package scan;

import org.springframework.boot.context.properties.ConfigurationProperties;

//import属性前缀，value 按import.value注入
@ConfigurationProperties("import")
public class ImportTest {
	String value;

	public ImportTest() {
		System.out.println(this.getClass().getName() + " construct");
	}

	public void print(){
		System.out.println(value);
	}
}
