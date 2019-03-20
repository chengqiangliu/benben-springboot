package com.benben.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * BaseRequest
 */
@Setter
@Getter
public class BaseRequest {

	private String cardId;

	private Date insertTime;

	private Date updateTime;
}
