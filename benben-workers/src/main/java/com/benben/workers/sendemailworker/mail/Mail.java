package com.benben.workers.sendemailworker.mail;

import lombok.*;

import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper=true)
public class Mail {

    private String to;

    private String subject;

    private String content;

    private Map model;

    private String template;
}
