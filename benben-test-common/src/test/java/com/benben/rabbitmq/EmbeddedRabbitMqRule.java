package com.benben.rabbitmq;


import org.junit.rules.ExternalResource;

public class EmbeddedRabbitMqRule extends ExternalResource {
    @Override protected void before() throws Throwable {
        EmbeddedRabbitMqWrapper.initEmbeddedRabbitMq();
    }

    @Override protected void after() {
        EmbeddedRabbitMqWrapper.stop();
    }
}
