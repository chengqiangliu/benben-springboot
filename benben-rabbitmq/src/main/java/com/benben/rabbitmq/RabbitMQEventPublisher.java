package com.benben.rabbitmq;

import com.benben.global.constants.BenbenConstants;
import com.benben.global.constants.ContextField;
import com.benben.logging.LoggerFormat;
import com.benben.logging.Loggers;
import com.benben.rabbitmq.basic.BasicConfirmRabbitPublisher;
import com.benben.rabbitmq.error.RabbitError;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Component
public class RabbitMQEventPublisher implements EventPublisher {

    private BasicConfirmRabbitPublisher rabbitPublisher;

    private String exchange;

    private Executor threadExecutor;

    private static final ObjectMapper OM = new ObjectMapper();


    @Autowired
    public RabbitMQEventPublisher(BasicConfirmRabbitPublisher rabbitPublisher,
                                  @Qualifier(BenbenConstants.ASYNC_EXECUTOR_BEAN_NAME) Executor threadExecutor,
                                  @Value("${event.publisher.exchange}") String exchange) {
        this.exchange = exchange;
        this.threadExecutor = threadExecutor;
        this.rabbitPublisher = rabbitPublisher;
    }

    @Override
    public void publish(Object event, String topic) {
        UUID uuid = UUID.randomUUID();
        MDC.put(ContextField.REQUEST_ID, uuid.toString());

        String requestId = MDC.get(ContextField.REQUEST_ID);
        String jsonStr = null;
        try {
            jsonStr = OM.writeValueAsString(event);

            asyncPublish(jsonStr, topic, requestId);
        } catch (Exception e) {
            RabbitError err = RabbitError.EVENT_PUBLISH_FAILED;
            Loggers.RABBITMQ_LOGGER.error(LoggerFormat.COMMON_EXCEPTION_WITH_PARAM, err.getCode(), err.getErrorMsg(), jsonStr,
                    e.getMessage(), e.getStackTrace());
        }
    }

    /**
     * rabbitPublisher.convertAndSend() blocks until it finds an opened connection / channel,
     * make this code async to give better response Time.
     *
     * @param jsonStr   json string
     * @param topic     topic of the events
     * @param requestId request id
     * @return completable future
     */
    private CompletableFuture<Void> asyncPublish(final String jsonStr, final String topic, final String requestId) {
        CompletableFuture<Void> cf = CompletableFuture.supplyAsync(() -> {
            MDC.put(ContextField.REQUEST_ID, requestId);
            try {
                this.rabbitPublisher.convertAndSend(this.exchange, topic, jsonStr, requestId);
                Loggers.RABBITMQ_LOGGER.info("published message message_id={}", requestId);
            } catch (Exception e) {
                RabbitError err = RabbitError.EVENT_PUBLISH_FAILED;
                Loggers.RABBITMQ_LOGGER.error(LoggerFormat.COMMON_EXCEPTION_WITH_PARAM, err.getCode(), err.getErrorMsg(), jsonStr,
                        e.getMessage(), e.getStackTrace());
            }
            return null;
        }, this.threadExecutor);
        return cf;
    }
}
