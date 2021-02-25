package com.benben.response;

import com.benben.global.http.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by liuchengqiang on 2018/10/25.
 */
@Setter
@Getter
public class SimpleResponse {

    protected ResponseStatus status = ResponseStatus.OK;
}
