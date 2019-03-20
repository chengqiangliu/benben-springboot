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
 * GetConsumeListRequest
 * 
 * @author:chengqiang.liu
 */
@Setter
@Getter
public class GetConsumeListRequest extends BaseQueryRequest {
	
	@Size(min=5, max=20)
	private String consumer;
	
	private Integer goodsType;
	
	private Date fromTime;
	
	private Date toTime;
	
	@Size(min=1, max=20)
	private String goodsName;
	
	@Min(1)
	@Max(2)
	private Integer paymentType;
	
	@Digits(integer=9, fraction=2)
	private BigDecimal goodsSum;
	
	private String sumFlag;
}
