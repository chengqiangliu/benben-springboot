package com.benben.rabbitmq.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RabbitError {

    NACK_RECEIVED("ELR9001", "NACK received from broker"),

    EVENT_PUBLISH_FAILED("ELR9002", "Event publish failed"),

    REPUB_FAILED("ELR9003", "Republish failed"),

    UNKNOWN_ERROR("ELR9999", "Unknown error");

    /** The code. */
    private final String code;

    /** The error msg. */
    private final String errorMsg;
}
