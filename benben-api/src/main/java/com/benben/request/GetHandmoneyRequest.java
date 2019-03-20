package com.benben.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

/**
 * GetHandmoneyRequest
 * 
 * @author:chengqiang.liu
 */
@Setter
@Getter
public class GetHandmoneyRequest extends BaseQueryRequest {
	
	@Size(min=5, max=20)
	private String holderName;
}
