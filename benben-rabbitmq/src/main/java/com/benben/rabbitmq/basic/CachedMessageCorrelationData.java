package com.benben.rabbitmq.basic;

import lombok.Getter;
import lombok.Setter;
import org.springframework.amqp.rabbit.support.CorrelationData;

@Setter
@Getter
public class CachedMessageCorrelationData extends CorrelationData {

    //Cache message
    private String message;

    CachedMessageCorrelationData(String messageId, String message) {
        this.setId(messageId);
        this.message = message;
    }
}
