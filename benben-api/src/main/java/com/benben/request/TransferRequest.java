package com.benben.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.Date;

/**
 * TransferRequest
 */
@Setter
@Getter
public class TransferRequest extends BaseRequest {

	private Integer transferId;

	private String srcCardId;

	private String targetCardId;

	@Digits(integer=9, fraction=2)
	private BigDecimal srcMoneyNum;

	@Digits(integer=9, fraction=2)
	private BigDecimal targetMoneyNum;

	@Digits(integer=9, fraction=2)
	private BigDecimal exchangeRate;
	
	private Date exchangeTime;

	private String exchanger;
}
