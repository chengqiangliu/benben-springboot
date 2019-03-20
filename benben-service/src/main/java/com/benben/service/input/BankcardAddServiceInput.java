package com.benben.service.input;

import com.benben.global.constants.BankcardType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
public class BankcardAddServiceInput {

    private String cardNo;

    private BankcardType cardType;

    private String bankType;

    private UUID accountId;

    private String currency;

    private BigDecimal creditSum;

    private BigDecimal balance;

    private Date issueDate;
}
