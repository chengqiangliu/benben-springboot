package com.benben.response;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by liuchengqiang on 2018/10/25.
 */
@Setter
@Getter
public class CashGetResponse {

    private String accountId;

    private String currency;

    private BigDecimal balance;

    private String insertTime;

    private String updateTime;
}
