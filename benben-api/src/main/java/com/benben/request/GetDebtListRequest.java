package com.benben.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * GetDebtListRequest
 * 
 * @author:chengqiang.liu
 */
@Setter
@Getter
public class GetDebtListRequest extends BaseQueryRequest {
	
	@Size(min=5, max=20)
	private String debterName;

	@Size(min=1, max=20)
	private String friend;

	@Min(value=0)
	@Max(value=1)
	private Integer debtType;
	
	@Min(1)
	@Max(2)
	private Integer paymentType;
	
	@Min(value=0)
	@Max(value=2)
	private Integer isPayoff;
	
	private Date fromTime;
	
	private Date toTime;
}
