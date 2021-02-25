package com.benben.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.Date;

/**
 * DepositRequest
 */
@Setter
@Getter
public class DepositRequest extends BaseRequest {

	private String cardId;
	
	private Date depositDate;

	@Digits(integer=9, fraction=2)
	private BigDecimal moneyNum;

	private String depositor;
}
