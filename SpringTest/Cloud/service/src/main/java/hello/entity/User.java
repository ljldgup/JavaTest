package hello.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;

//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;

//@Entity // This tells Hibernate to make a table out of this class
@Data
public class User {

	@TableId("ID")
    private Long id;

    private String name;

    private String email;

	private BigDecimal amount;
}

