package com.benben.service.input;

import com.benben.global.constants.BankcardType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
public class BankcardUpdateServiceInput {

    private String cardNo;

    private BankcardType cardType;

    private String bankType;

    private BigDecimal creditSum;

    private BigDecimal balance;
}
