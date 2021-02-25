package com.benben.runner;

import com.benben.batch.BenbenBatch;
import com.benben.error.BenbenBatchError;
import com.benben.exception.BatchValidationException;
import com.benben.global.BatchCommands;
import com.benben.global.constants.ContextField;
import com.benben.logging.Loggers;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.UUID;

@Component
public class BenbenBatchRunner implements CommandLineRunner {

    public static final String COMMAND = "command";

    public static final String START_TIME = "startTime";

    public static final String END_TIME = "endTime";

    private static String command = null;

    private static Instant startTimeInstant = null;

    private static Instant endTimeInstant = null;

    @Autowired
    private BenbenBatch batch;

    @Autowired
    private AbstractApplicationContext applicationContext;

    @Override
    public void run(String... args) {
        this.applicationContext.registerShutdownHook();
        int result = startBatchOperation(args);
        System.exit(result);
    }

    public int startBatchOperation(String... args) {
        MDC.clear();
        MDC.put(ContextField.REQUEST_ID, UUID.randomUUID().toString());

        parseCommandParameters(args);

        if (command == null) {
            throw new BatchValidationException(BenbenBatchError.MISSING_COMMAND);
        }

        switch (command) {
            case BatchCommands.command1:
                this.batch.process(startTimeInstant, endTimeInstant);
                break;
            case BatchCommands.command2:
                this.batch.process2(startTimeInstant, endTimeInstant);
                break;
            default:
                Loggers.BATCH.warn("Invalid command: {}", command);
                throw new BatchValidationException(
                        BenbenBatchError.INVALID_COMMAND);
        }
        return 0;
    }

    private void parseCommandParameters(String... args) throws BatchValidationException {
        Options batchOptions = new Options();
        batchOptions.addOption(COMMAND, true, "Operation Name")
                .addOption(START_TIME, true, "Start Time")
                .addOption(END_TIME, true, "End Time");

        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(batchOptions, args, true);
            for (Option option : cmd.getOptions()) {
                if (COMMAND.equals(option.getOpt())) {
                    command = option.getValue();
                }
                if (START_TIME.equals(option.getOpt())) {
                    startTimeInstant = Instant.parse(option.getValue());
                }
                if (END_TIME.equals(option.getOpt())) {
                    endTimeInstant = Instant.parse(option.getValue());
                }
            }
        } catch (org.apache.commons.cli.ParseException | DateTimeParseException e) {
            throw new BatchValidationException(BenbenBatchError.INVALID_COMMAND, e);
        }
    }
}
