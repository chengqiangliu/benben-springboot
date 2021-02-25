package com.benben.request;

import lombok.Getter;
import lombok.Setter;

/**
 * GetMoneySumRequest
 * 
 * @author:chengqiang.liu
 */
@Setter
@Getter
public class GetMoneySumRequest extends BaseQueryRequest {
	
	private String moneySumType;
}
