package com.benben.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * GetGeneralTypeListRequest
 * 
 * @author:chengqiang.liu
 */
@Setter
@Getter
public class GetGeneralTypeListRequest {
	
	private int generalType;
	
	private List<String> generalTypeNameList;
	
	private List<GeneralType> generalTypeList;
}
