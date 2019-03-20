package com.benben.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.List;

/**
 * GroupRequest
 */
@Setter
@Getter
public class GroupRequest {

	@NotNull
	@Min(10001)
	@Max(99999)
	private Integer groupNo;

	@NotNull
	@Pattern(regexp="^[a-zA-Z 0-9_]{3,20}$")
	private String groupName;
	
	private boolean adminGroup;
	
	private List<String> apiList;
}
