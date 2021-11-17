package ljl.entity;

import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import scan.ImportTest;

@Service
@Import(ImportTest.class)
public class BeanTest {
}
