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
 * ConsumeRequest
 */
@Setter
@Getter
public class ConsumeRequest extends BaseRequest {

	/** consume_id. */
	private Integer consumeId;

	/** goods_name. */
	@Size(min=1, max=20)
	private String goodsName;

	/** goods_type. */
	private Integer goodsType;

	/** goods_count. */
	@Digits(integer=9, fraction=2)
	private BigDecimal goodsCount;

	/** price. */
	@Digits(integer=9, fraction=2)
	private BigDecimal price;

	/** currency_id. */
	private Integer currency;

	/** goods_sum. */
	@Digits(integer=9, fraction=2)
	private BigDecimal goodsSum;

	/** user_table. */
	private String consumer;

	/** 付款方式 */
	@Min(1)
	@Max(2)
	private Integer paymentType;
	
	/** card_id. */
	private String cardId;

	/** consume_time. */
	private Date consumeTime;

}
