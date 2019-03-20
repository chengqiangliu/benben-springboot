package com.benben.rabbitmq.basic;

import com.benben.global.constants.ContextField;
import com.benben.logging.LoggerFormat;
import com.benben.logging.Loggers;
import com.benben.rabbitmq.error.RabbitError;
import org.slf4j.MDC;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Component;

@Component
public class BasicConfirmRabbitPublisher {

    /**
     * Rabbit template
     */
    private RabbitTemplate rabbitTemplate;

    /**
     * Constructor
     *
     * @param rabbitTemplate a rabbit template
     */
    public BasicConfirmRabbitPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setConfirmCallback(this::confirmCallback);
    }

    /**
     * Conver and Send methods that put jsonStr into cache for callback purpose
     *
     * @param exchange   target exchange
     * @param routingKey message routingKey
     * @param jsonStr    jsonStr of message body
     * @param messageId  messageId
     */
    public void convertAndSend(String exchange, String routingKey, String jsonStr, String messageId) {
        //cache the message boy
        this.rabbitTemplate.convertAndSend(exchange, routingKey, jsonStr, (m) -> {
            m.getMessageProperties().setMessageId(messageId);
            return m;
        }, new CachedMessageCorrelationData(messageId, jsonStr));
    }

    /**
     * Confirm callback
     *
     * @param correlationData correlation data
     * @param ack             true: ack, false: nack
     * @param cause           cause of error
     */
    protected void confirmCallback(CorrelationData correlationData, boolean ack, String cause) {
        final String requestId = correlationData.getId();
        MDC.clear();
        MDC.put(ContextField.REQUEST_ID, requestId);
        if (ack) {
            Loggers.RABBITMQ_LOGGER.info("pub|ok");
        } else {
            CachedMessageCorrelationData data = (CachedMessageCorrelationData) correlationData;
            String msg = data.getMessage();
            RabbitError err = RabbitError.NACK_RECEIVED;
            Loggers.RABBITMQ_LOGGER.error(LoggerFormat.COMMON_EXCEPTION_WITH_PARAM, err.getCode(), err.getErrorMsg(), msg, cause);
        }
    }
}
