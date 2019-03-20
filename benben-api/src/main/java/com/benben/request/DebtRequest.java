package com.benben.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

/**
 * DebtRequest
 */
@Setter
@Getter
public class DebtRequest extends BaseRequest {

	/** debt_id. */
	private Integer debtId;

	/** borrow_time. */
	private Date borrowTime;

	/** card. */
	private String cardId;

	/** debt_type. */
	@Min(value=0)
	@Max(value=1)
	private Integer debtType;

	/** friend. */
	@Size(min=1, max=20)
	private String friend;

	/** has_not_paid_num. */
	@Digits(integer=9, fraction=2)
	private BigDecimal hasNotPaidNum;

	/** has_paid_num. */
	@Digits(integer=9, fraction=2)
	private BigDecimal hasPaidNum;

	/** is_payoff. */
	@Min(value=0)
	@Max(value=2)
	private Integer isPayoff;

	/** src_money_num. */
	@Digits(integer=9, fraction=2)
	private BigDecimal srcMoneyNum;

	/** payment_type. */
	@Min(1)
	@Max(2)
	private Integer paymentType;

	/** user_table. */
	private String debtor;

	/** currency_id. */
	private Integer currency;

}
