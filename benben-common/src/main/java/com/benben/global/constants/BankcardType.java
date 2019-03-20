package com.benben.global.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BankcardType {

    CREDIT_CARD("CREDIT_CARD", "CREDIT_CARD"),

    SAVING_CARD("SAVING_CARD", "SAVING_CARD"),

    ;

    private String name;

    private String desc;
}
