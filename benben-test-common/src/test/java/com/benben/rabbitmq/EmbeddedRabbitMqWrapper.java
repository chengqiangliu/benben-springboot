package com.benben.rabbitmq;

import com.benben.logging.Loggers;
import io.arivera.oss.embedded.rabbitmq.EmbeddedRabbitMq;
import io.arivera.oss.embedded.rabbitmq.EmbeddedRabbitMqConfig;
import io.arivera.oss.embedded.rabbitmq.PredefinedVersion;
import io.arivera.oss.embedded.rabbitmq.RabbitMqEnvVar;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;

public class EmbeddedRabbitMqWrapper {
    private static final String HOST = "localhost";

    /**
     * default as 0, rabbitMq port
     */
    private static int port;

    private static ConnectionFactory connectionFactory;

    private static AmqpAdmin rabbitAdmin;

    private static EmbeddedRabbitMq rabbitMq;

    /**
     * start rabbitMq with random Port, then create shared connectionFactory and rabbitAdmin
     */
    public static void initEmbeddedRabbitMq() {
        Loggers.TEST.info("Initializing Embedded RabbitMq.");
        EmbeddedRabbitMqConfig config = new EmbeddedRabbitMqConfig.Builder()
                .version(PredefinedVersion.V3_6_9)
                .envVar(RabbitMqEnvVar.NODENAME, "rabbit@localhost")
                .defaultRabbitMqCtlTimeoutInMillis(30000)
                .rabbitMqServerInitializationTimeoutInMillis(30000)
                .randomPort().build();
        rabbitMq = new EmbeddedRabbitMq(config);
        rabbitMq.start();

        port = config.getRabbitMqPort();
        connectionFactory = new CachingConnectionFactory(HOST, port);
        rabbitAdmin = new RabbitAdmin(connectionFactory);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                Loggers.TEST.info("force shutdown embedded rabbitMq server");
                EmbeddedRabbitMqWrapper.stop();
            } catch (Exception e) {
                // ignore the exception here
                Loggers.TEST.warn("exception while force shutdown", e);
            }
        }));
        Loggers.TEST.info("Embedded RabbitMq initialized.");
    }

    /**
     * This method will try to load embedded rabbitMq if not loaded,
     * and will do nothing if already loaded.
     */
    public static void tryInitEmbeddedRabbitMq() {
        if (connectionFactory == null) {
            Loggers.TEST.info("Initializing Embedded RabbitMq...");
            initEmbeddedRabbitMq();
        } else {
            Loggers.TEST.info("Embedded RabbitMq is already initialized, do nothing.");
        }
    }

    /**
     * stop rabbitMq
     */
    public static void stop() {
        Loggers.TEST.info("Stopping Embedded RabbitMq.");
        rabbitMq.stop();
        connectionFactory = null;
    }

    public static ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    public static int getPort() {
        return port;
    }

    /**
     * Declare the given queue
     *
     * @param queueName the name of the queue
     */
    public static void declareQueue(String queueName) {
        rabbitAdmin.declareQueue(new Queue(queueName));
    }

    /**
     * Purges the contents of the given queue.
     *
     * @param queueName the name of the queue
     * @param noWait    true to not await completion of the purge
     */
    public static void purgeQueue(String queueName, boolean noWait) {
        rabbitAdmin.purgeQueue(queueName, noWait);
    }

    public static AmqpAdmin getAdmin() {
        return rabbitAdmin;
    }
}
