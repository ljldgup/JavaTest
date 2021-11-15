package scan;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
public class ScanTest {
	@Value("${random.uuid}")
	String uuid;

	public void print(){
		System.out.println(uuid);
	}
}
