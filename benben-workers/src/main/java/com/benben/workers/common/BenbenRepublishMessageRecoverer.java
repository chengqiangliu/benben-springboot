package com.benben.workers.common;

import com.benben.logging.LoggerFormat;
import com.benben.logging.Loggers;
import com.benben.rabbitmq.error.BenbenRabbitmqException;
import com.benben.rabbitmq.error.RabbitError;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;

public class BenbenRepublishMessageRecoverer extends RepublishMessageRecoverer {

    /**
     * Just calling the constructor from parent
     *
     * @param errorTemplate   the error template
     */
    public BenbenRepublishMessageRecoverer(AmqpTemplate errorTemplate) {
        super(errorTemplate);
    }

    /**
     * Just calling the constructor from parent
     *
     * @param errorTemplate   the error template
     * @param errorExchange   the error exchange
     */
    public BenbenRepublishMessageRecoverer(AmqpTemplate errorTemplate,
                                        String errorExchange) {
        super(errorTemplate, errorExchange);
    }

    /**
     * Just calling the constructor from parent
     *
     * @param errorTemplate   the error template
     * @param errorExchange   the error exchange
     * @param errorRoutingKey the error routing key
     */
    public BenbenRepublishMessageRecoverer(AmqpTemplate errorTemplate,
                                        String errorExchange, String errorRoutingKey) {
        super(errorTemplate, errorExchange, errorRoutingKey);
    }

    @Override
    public void recover(Message message, Throwable cause) {
        Loggers.RABBITMQ_LOGGER.warn("con|exhausted|{}", new String(message.getBody()));
        Throwable rootCause = Loggers.getRootCause(cause);
        RabbitError err = RabbitError.UNKNOWN_ERROR;

        if (rootCause instanceof BenbenRabbitmqException) {
            err = ((BenbenRabbitmqException) rootCause).getError();
        }

        Loggers.RABBITMQ_LOGGER.error(LoggerFormat.COMMON_EXCEPTION_WITH_PARAM, err.getCode(), err.getErrorMsg(), rootCause.toString(),
                rootCause.getStackTrace());

        try {
            super.recover(message, cause);
        } catch (AmqpException aEx) {
            RabbitError repubErr = RabbitError.REPUB_FAILED;
            Loggers.RABBITMQ_LOGGER.error(LoggerFormat.COMMON_EXCEPTION_WITH_PARAM, repubErr.getCode(), repubErr.getErrorMsg(), aEx.toString(),
                    aEx.getStackTrace());
            throw aEx;
        }
    }
}
