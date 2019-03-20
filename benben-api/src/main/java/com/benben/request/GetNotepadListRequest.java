package com.benben.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * GetNotepadListRequest
 * 
 * @author:chengqiang.liu
 */
@Setter
@Getter
public class GetNotepadListRequest extends BaseQueryRequest {
	
	@Size(min=1, max=20)
	private String notepadTitle;

	@Min(value=1)
	@Max(value=2)
	private Integer notepadType;
}
