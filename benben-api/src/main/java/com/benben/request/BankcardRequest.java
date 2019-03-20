package com.benben.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * BankcardRequest
 */
@Setter
@Getter
public class BankcardRequest extends BaseRequest {

	private String cardType;

	private String bankType;
	
	@Pattern(regexp="^[0-9]{10,19}$")
	private String cardNo;
	
	private UUID accountId;
	
	private String currency;
	
	@Digits(integer=9, fraction=2)
	private BigDecimal creditSum;

	@Digits(integer=9, fraction=2)
	private BigDecimal cardBalance;

	private Date issueDate;
}
