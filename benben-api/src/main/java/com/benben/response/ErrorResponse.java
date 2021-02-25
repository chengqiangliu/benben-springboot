package com.benben.response;

import com.benben.errorhandler.ErrorItem;
import com.benben.global.http.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ErrorResponse {

    /** Response Status */
    private ResponseStatus status = ResponseStatus.ERROR;

    /** Error List */
    private List<ErrorItem> errors;
}
