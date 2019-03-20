package com.benben.global.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApiName {

    UNKNOWN("UNKNOWN", "UNKNOWN"),

    REGISTER_USER("REGISTER_USER", "REGISTER_USER"),

    DELETE_USER("DELETE_USER", "DELETE_USER"),

    EDIT_USER("EDIT_USER", "EDIT_USER"),

    GET_USER("GET_USER", "GET_USER"),

    ADD_BANKCARD("ADD_BANKCARD", "ADD_BANKCARD"),

    ADD_GROUP("ADD_GROUP", "ADD_GROUP"),

    UPDATE_GROUP("UPDATE_GROUP", "UPDATE_GROUP"),

    DELETE_GROUP("DELETE_GROUP", "DELETE_GROUP"),

    GROUP_LIST("GROUP_LIST", "GROUP_LIST"),

    GET_GROUP("GET_GROUP", "GET_GROUP"),

    ADD_CASH("ADD_CASH", "ADD_CASH"),

    UPDATE_CASH("UPDATE_CASH", "UPDATE_CASH"),

    DELETE_CASH("DELETE_CASH", "DELETE_CASH"),

    GET_CASH("GET_CASH", "GET_CASH"),

    ;

    private String name;

    private String desc;
}
