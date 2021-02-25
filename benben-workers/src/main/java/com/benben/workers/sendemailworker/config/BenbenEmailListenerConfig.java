package com.benben.workers.sendemailworker.config;

import com.benben.workers.common.BenbenRepublishMessageRecoverer;
import com.benben.workers.common.CreateListenerContainer;
import com.benben.workers.sendemailworker.BenbenEmailListener;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.retry.MissingMessageIdAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.retry.interceptor.StatefulRetryOperationsInterceptor;
import org.springframework.retry.policy.MapRetryContextCache;

@Configuration
public class BenbenEmailListenerConfig {

    private static final String THREAD_PREFIX = "SendEmailWorkerThread-";

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private BenbenEmailListener listener;

    @Value("${workers.send-email-worker.queue.name}")
    private String queueName;

    @Value("${workers.send-email-worker.pool.size}")
    private int corePoolSize;

    @Value("${workers.send-email-worker.pool.max}")
    private int maxPoolSize;

    @Value("${workers.send-email-worker.poison.queue.name}")
    private String poisonQueue;

    @Value("${workers.send-email-worker.retry.backOff.initial}")
    private int backOffInit;

    @Value("${workers.send-email-worker.retry.backOff.max}")
    private int backOffMax;

    @Value("${workers.send-email-worker.retry.max}")
    private int maxRetry;

    @Value("${workers.send-email-worker.retry.backOff.multiplier}")
    private float backOffMultiplier;

    /**
     * Message Listener
     *
     * @return a listener for the queue
     */
    @Bean
    @Conditional(value = BeanCondition.class)
    public SimpleMessageListenerContainer streamingAnalyticsListenerContainer() {
        SimpleMessageListenerContainer container = CreateListenerContainer
                .createListenerContainer(this.connectionFactory, this.queueName,
                        this.corePoolSize,
                        this.maxPoolSize,
                        THREAD_PREFIX, this.listener);

        MissingMessageIdAdvice missingMessageIdAdvice = new MissingMessageIdAdvice(new MapRetryContextCache());

        // use default exchange with poison queue as routing key
        BenbenRepublishMessageRecoverer recoverer = new BenbenRepublishMessageRecoverer(this.template, "", this.poisonQueue);
        //provide empty string to have no prefix for routing key
        recoverer.setErrorRoutingKeyPrefix("");
        StatefulRetryOperationsInterceptor interceptor = RetryInterceptorBuilder.stateful().maxAttempts(5)
                .backOffOptions(this.backOffInit, this.backOffMultiplier, this.backOffMax).recoverer(recoverer)
                .build();

        container.setAdviceChain(missingMessageIdAdvice, interceptor);
        return container;
    }

    /**
     * Determine whether send-email-worker should be registered
     */
    public static class BeanCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            Environment env = context.getEnvironment();
            return Boolean.parseBoolean(env.getProperty("workers.send-email-worker.enable"));
        }
    }
}
