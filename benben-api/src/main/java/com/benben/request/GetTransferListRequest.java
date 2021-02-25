package com.benben.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.sql.Timestamp;

/**
 * GetTransferListRequest
 * 
 * @author:chengqiang.liu
 */
@Setter
@Getter
public class GetTransferListRequest extends BaseQueryRequest {
	
	/** 转账元卡开户银行 */
	private String srcBankType;
	
	/** 转账目标卡开户银行 */
	private String targetBankType;
	
	/** 开始时间 */
	private Timestamp fromTime;
	
	/** 结束时间 */
	private Timestamp toTime;
	
	/** 转账人 */
	@Size(min=5, max=20)
	private String exchangerName;
}
