package com.benben.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * CommonQueryRequest
 * 
 */
@Setter
@Getter
public class BaseQueryRequest {
	
	private String sortKey;
	
	private String sortType;
	
	private int currentPage;
	
	@Min(value=0)
	@Max(value=99)
	private int recordPerPage;
	
	private Integer currency;
}
