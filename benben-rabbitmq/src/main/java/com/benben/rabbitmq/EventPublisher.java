package com.benben.rabbitmq;

public interface EventPublisher {

    void publish(Object event, String topic);
}
