package com.benben.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.Date;

/**
 * WithdrawalRequest
 */
@Setter
@Getter
public class WithdrawalRequest extends BaseRequest {

	private String cardId;
	
	private Date withdrawalDate;

	@Digits(integer=9, fraction=2)
	private BigDecimal moneyNum;

	private String withdrawaler;
}
