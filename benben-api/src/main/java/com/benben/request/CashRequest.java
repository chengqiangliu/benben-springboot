package com.benben.request;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * GroupRequest
 */
@Setter
@Getter
public class CashRequest {

	@NotNull
	@Size(min=5, max=20)
	private String username;

	@NotNull
	@Pattern(regexp="^[A-Z0]{3}$")
	private String currency;

	@NotNull
	private BigDecimal balance;
}
