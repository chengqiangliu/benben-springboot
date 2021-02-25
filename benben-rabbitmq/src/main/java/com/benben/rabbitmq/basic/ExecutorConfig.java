package com.benben.rabbitmq.basic;

import com.benben.global.constants.BenbenConstants;
import com.benben.logging.Loggers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class ExecutorConfig {

    private static final String ASYNC_PREFIX = "BenbenWorkerAsync";

    @Value("#{new Integer(${async.corePoolSize:10})}")
    private int corePoolSize;

    @Value("#{new Integer(${async.maxPoolSize:50})}")
    private int maxPoolSize;

    @Value("#{new Integer(${async.queueCapacity:50})}")
    private int queueCapacity;

    @Value("#{new Integer(${recoveryWorkerAsync.awaitTerminationSeconds:30})}")
    private int awaitTerminationSeconds;

    /** Executor init log format */
    private static final String EXECUTOR_INIT_FORMAT = "{} is initialized";

    @Bean(name = BenbenConstants.ASYNC_EXECUTOR_BEAN_NAME)
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(this.corePoolSize);
        threadPoolTaskExecutor.setMaxPoolSize(this.maxPoolSize);
        threadPoolTaskExecutor.setQueueCapacity(this.queueCapacity);
        threadPoolTaskExecutor.setThreadNamePrefix(ASYNC_PREFIX);
        threadPoolTaskExecutor.initialize();
        Loggers.RABBITMQ_LOGGER.info("Async Thread Pool initialized corePoolSize={}, maxPoolSize={}, queueCapacity={}",
                        this.corePoolSize, this.maxPoolSize, this.queueCapacity);

        // set to true for graceful shutdown
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        threadPoolTaskExecutor.setAwaitTerminationSeconds(this.awaitTerminationSeconds);

        Loggers.RABBITMQ_LOGGER.info(EXECUTOR_INIT_FORMAT, BenbenConstants.ASYNC_EXECUTOR_BEAN_NAME);
        return threadPoolTaskExecutor;
    }
}
