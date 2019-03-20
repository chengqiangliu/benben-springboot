package com.benben.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * NotepadRequest
 */
@Setter
@Getter
public class NotepadRequest extends BaseRequest {

	/** notepad_id. */
	private Integer notepadId;

	/** notepad_title. */
	@Size(min=1, max=20)
	private String notepadTitle;

	/** notepad_type. */
	@Min(value=1)
	@Max(value=2)
	private Integer notepadType;

	/** notepad_content. */
	private String notepadContent;
}
