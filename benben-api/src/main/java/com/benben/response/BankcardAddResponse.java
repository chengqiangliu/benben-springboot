package com.benben.response;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by liuchengqiang on 2018/10/25.
 */
@Setter
@Getter
public class BankcardAddResponse extends SimpleResponse {

    private String cardNo;

    private String issueDate;
}
