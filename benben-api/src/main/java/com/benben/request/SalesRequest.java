package com.benben.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

/**
 * SalesRequest
 */
@Setter
@Getter
public class SalesRequest extends BaseRequest {

	private Integer salesId;

	private String cardId;

	@Digits(integer=9, fraction=2)
	private BigDecimal goodsCount;

	@Size(min=1, max=20)
	private String goodsName;

	@Size(min=1, max=20)
	private String goodsSource;

	@Digits(integer=9, fraction=2)
	private BigDecimal goodsSum;

	private Integer goodsType;

	@Digits(integer=9, fraction=2)
	private BigDecimal price;

	private Date saleTime;

	private Integer paymentType;

	private String salelor;

	private Integer currency;
}
