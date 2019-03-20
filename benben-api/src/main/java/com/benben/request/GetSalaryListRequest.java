package com.benben.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.Date;

/**
 * GetSalaryListRequest
 * 
 * @author:chengqiang.liu
 */
@Setter
@Getter
public class GetSalaryListRequest extends BaseQueryRequest {
	
	@Size(min=5, max=20)
	private String ownerName;
	
	private Date fromTime;
	
	private Date toTime;
}
