ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender

appender("STDOUT", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d{yyyy-MM-dd'T'HH:mm:ss.SSS}\\t[%p]\\t[%c]\\t|%m|\\t%t\\t%F\\t%L\\t%X{MESSAGE_ID}%n"
    }
}

appender("BENBEN_CONSOLE", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d{yyyy-MM-dd'T'HH:mm:ss.SSS}\\t[%p]\\t[%c]\\t|%X{API_NAME}|%m|\\t%t\\t%F\\t%L\\t%X{MESSAGE_ID}%n"
    }
}

appender("BENBEN_ADVICE", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d{yyyy-MM-dd'T'HH:mm:ss.SSS}\\t[%p]\\t[%c]\\t|%X{API_NAME}|%m|url=%X{REQ_URL}|\\t%t\\t%F\\t%L\\t%X{MESSAGE_ID}%n"
    }
}

// Don't need output log
logger("org.springframework", ERROR, ["STDOUT"], false)
logger("ch.qos.logback", ERROR, ["STDOUT"], false)

logger("BenbenAPI", INFO, ["BENBEN_CONSOLE"], false)
logger("BenbenService", INFO, ["BENBEN_ADVICE"], false)
logger("BenbenDao", INFO, ["BENBEN_ADVICE"], false)
logger("BenbenRabbitmq", INFO, ["BENBEN_CONSOLE"], false)
logger("BenbenWorker", INFO, ["BENBEN_ADVICE"], false)
logger("Performance", INFO, ["BENBEN_ADVICE"], false)

root(INFO, ["STDOUT"])