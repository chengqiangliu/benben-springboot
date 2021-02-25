package com.benben.service.input;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
public class CashServiceInput {

    private String username;

    private String currency;

    private BigDecimal balance;
}
