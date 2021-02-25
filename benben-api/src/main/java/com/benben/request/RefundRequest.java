package com.benben.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.Date;

/**
 * RefundRequest
 */
@Setter
@Getter
public class RefundRequest extends BaseRequest {

	/** refund_id. */
	private Integer refundId;

	/** card_id. */
	private String cardId;

	/** debt_id. */
	private String debtId;

	/** refund_date. */
	private Date refundDate;

	/** refund_money_um. */
	@Digits(integer=9, fraction=2)
	private BigDecimal refundMoneySum;

	/** refund_type. */
	@Min(0)
	@Max(1)
	private Integer refundType;

	/** payment_type. */
	@Min(1)
	@Max(2)
	private Integer paymentType;

	/** currency_id. */
	private Integer currency;
	
	/** refunder. */
	private String refunder;
}
