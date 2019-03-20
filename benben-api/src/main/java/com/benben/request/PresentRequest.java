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
 * PresentRequest
 */
@Setter
@Getter
public class PresentRequest extends BaseRequest {

	/** present_id. */
	private Integer presentId;

	/** card_id. */
	private String cardId;

	/** friend. */
	@Size(min=1, max=20)
	private String friend;

	/** present_type. */
	@Min(value=1)
	@Max(value=3)
	private Integer presentType;

	/** present_name. */
	@Size(min=1, max=20)
	private String presentName;

	/** present_num. */
	@Digits(integer=9, fraction=2)
	private BigDecimal presentNum;

	/** money_sum. */
	@Digits(integer=9, fraction=2)
	private BigDecimal moneySum;

	/** send_time. */
	private Date sendTime;

	/** send_type. */
	@Min(value=0)
	@Max(value=1)
	private Integer sendType;

	/** sendor_id. */
	private String sendor;

	/** currency_id. */
	private Integer currency;
}
