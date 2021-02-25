package com.benben.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.*;

/**
 * UserRequest
 */
@Setter
@Getter
public class UserRequest {

	/** username. */
	@NotNull
	@Size(min=5, max=20)
	private String username;

	/** password. */
	@NotNull
	@Pattern(regexp="^[a-zA-Z0-9_]{3,20}$")
	private String password;
	
	@Email
	private String email;
	
	private String groupNo;
}
