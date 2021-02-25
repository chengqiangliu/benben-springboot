package com.benben.global.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmailTemplate {

    CREATE_USER_TEMPLATE("CREATE_USER_TEMPLATE", "create-user-email-template.ftl"),
    UPDATE_USER_TEMPLATE("CREATE_USER_TEMPLATE", "update-user-email-template.ftl");

    private String name;

    private String value;
}
