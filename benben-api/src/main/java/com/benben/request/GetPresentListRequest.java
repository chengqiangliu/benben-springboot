package com.benben.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * GetPresentListRequest
 * 
 * @author:chengqiang.liu
 */
@Setter
@Getter
public class GetPresentListRequest extends BaseQueryRequest {
	
	@Size(min=5, max=20)
	private String sendorName;
	
	@Min(value=0)
	@Max(value=1)
	private Integer sendType;
	
	@Min(value=1)
	@Max(value=3)
	private Integer presentType;
	
	private Date fromTime;
	
	private Date toTime;
}
