package com.benben.workers.common;

import com.benben.logging.Loggers;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class CreateListenerContainer {

    @Value("${rabbitmq.queues.terminateWaitTime:60}")
    private static int awaitTerminationSeconds;

    public static SimpleMessageListenerContainer createListenerContainer(ConnectionFactory connectionFactory,
                           String queueName, int corePoolSize, int maxPoolSize, String threadPrefix, Object messageListener) {
        final Queue queue = new Queue(queueName);

        // set up executor pool
        final ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(corePoolSize);
        taskExecutor.setMaxPoolSize(maxPoolSize);
        taskExecutor.setThreadNamePrefix(threadPrefix);
        taskExecutor.afterPropertiesSet();
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        taskExecutor.setAwaitTerminationSeconds(awaitTerminationSeconds);

        Loggers.WORKER_LOGGER.info(
                "Configured taskExecutor with queueName={}|poolSize={}|maxPoolSize={}|threadPrefix={}",
                queueName, corePoolSize, maxPoolSize, threadPrefix);

        // configure the listener container
        final SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer();
        listenerContainer.setConnectionFactory(connectionFactory);
        listenerContainer.setQueues(queue);
        listenerContainer.setMessageListener(messageListener);
        listenerContainer.setAcknowledgeMode(AcknowledgeMode.AUTO);

        Loggers.WORKER_LOGGER.info(
                "Configured listenerContainer with connectionFactory: {}, queue: {}, messageListener: {}, acknowledgeMode: {}",
                connectionFactory, queue, messageListener, AcknowledgeMode.AUTO);

        // configure concurrent consumers
        listenerContainer.setTaskExecutor(taskExecutor);
        listenerContainer.setConcurrentConsumers(corePoolSize);

        Loggers.WORKER_LOGGER.info("Configured concurrent consumers with taskExecutor{}, poolSize: {} ",
                taskExecutor, corePoolSize);

        return listenerContainer;
    }
}
