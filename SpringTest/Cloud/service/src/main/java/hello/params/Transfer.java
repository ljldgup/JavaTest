package hello.params;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Transfer {
	Long from;

	Long to;

	BigDecimal amount;
}
