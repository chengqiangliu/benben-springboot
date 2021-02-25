package com.benben.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

/**
 * GetBankcardListRequest
 * 
 * @author:chengqiang.liu
 */
@Setter
@Getter
public class GetBankcardListRequest extends BaseQueryRequest {
	
	/** 卡的类型（银行卡，信用卡，医保卡等） */
	private String cardType;
	
	/** 开户银行 */
	private Integer bankType;
	
	/** 持卡人 */
	@Size(min=5, max=20)
	private String holderName;
}
