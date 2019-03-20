package com.benben.global.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CurrencyCode {

    CNY("CNY", "CNY"),

    JPY("JPY", "JPY"),

    USD("USD", "USD"),

    ;

    private String name;

    private String desc;
}
