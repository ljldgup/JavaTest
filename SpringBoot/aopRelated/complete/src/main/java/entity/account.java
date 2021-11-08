package entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class Account {

	@TableId("ID")
    private Long id;

    private String name;

    private String email;

	private BigDecimal amount;
}

