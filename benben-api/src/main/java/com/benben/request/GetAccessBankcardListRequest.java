package com.benben.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

/**
 * GetAccessBankcardListRequest
 * 
 * @author:chengqiang.liu
 */
@Setter
@Getter
public class GetAccessBankcardListRequest extends BaseQueryRequest {

	/** 持卡人 */
	@Size(min=5, max=20)
	private String accessorName;
	
	/** 开户银行 */
	private Integer bankType;
	
	/** access_type. */
	@Min(0)
	@Max(2)
	private Integer accessType;
	
	/** 开始时间 */
	private Timestamp fromTime;
	
	/** 结束时间 */
	private Timestamp toTime;
}
