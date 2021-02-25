package com.benben.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * GetSalesListRequest
 * 
 * @author:chengqiang.liu
 */
@Setter
@Getter
public class GetSalesListRequest extends BaseQueryRequest {
	
	/** 销售者 */
	@Size(min=5, max=20)
	private String salerName;
	
	/** 消售类别 */
	private Integer goodsType;
	
	/** 开始时间 */
	private Timestamp fromTime;
	
	/** 结束时间 */
	private Timestamp toTime;
	
	/** 付款方式 */
	@Min(1)
	@Max(2)
	private Integer paymentType;
	
	/** 总价 */
	@Digits(integer=9, fraction=2)
	private BigDecimal goodsSum;
	
	/** 商品名 */
	@Size(min=1, max=20)
	private String goodsName;
	
	/** 总价flag */
	private String sumFlag;
}
