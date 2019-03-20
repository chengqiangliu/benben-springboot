package com.benben.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * GetUserListRequest
 * 
 * @author:chengqiang.liu
 */
@Setter
@Getter
public class GetUserListRequest extends BaseQueryRequest {
	
	/** 用户名 */
	@Size(min=5, max=20)
	private String username;
	
	/** 锁定状态(0:非锁定状态，1:锁定状态) */
	@Min(0)
	@Max(1)
	private Integer lockStatus;
	
	/** 查询登陆用户和锁定用户的条件 */
	private String searchUserCondition;
}
