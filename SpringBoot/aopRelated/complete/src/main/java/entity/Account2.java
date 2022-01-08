package entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;

@Data
@TableName("account")
public class Account2 {
	@TableId("ID")
	private Long id;

	private String name;

	private String email;

	private BigDecimal amount;
}
