package com.benben.rabbitmq.error;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BenbenRabbitmqException extends RuntimeException {

    private RabbitError error;

    BenbenRabbitmqException(RabbitError error) {
        this.error = error;
    }
}
