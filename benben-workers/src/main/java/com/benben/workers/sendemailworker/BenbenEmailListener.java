package com.benben.workers.sendemailworker;

import com.benben.global.constants.ContextField;
import com.benben.global.constants.EmailTemplate;
import com.benben.global.events.CreateUserEvent;
import com.benben.logging.LoggerFormat;
import com.benben.logging.Loggers;
import com.benben.workers.sendemailworker.mail.EmailSender;
import com.benben.workers.sendemailworker.mail.Mail;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.TemplateException;
import org.slf4j.MDC;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
public class BenbenEmailListener implements MessageListener {

    @Autowired
    private EmailSender mailSender;

    private static ObjectMapper mapper = new ObjectMapper();

    @Override
    public void onMessage(Message message) {
        MDC.clear();
        MDC.put(ContextField.REQUEST_ID, message.getMessageProperties().getMessageId());
        String msgBody = new String(message.getBody(), StandardCharsets.UTF_8);

        CreateUserEvent event;
        try {
            event = mapper.readValue(msgBody, CreateUserEvent.class);
        } catch (IOException e) {
            Loggers.WORKER_LOGGER.error(LoggerFormat.COMMON_EXCEPTION, Loggers.getRootCause(e));
            return;
        }

        Mail mail = new Mail();
        mail.setTo(event.getEmail());
        mail.setSubject("Your Account is registered successfully");
        mail.setTemplate(EmailTemplate.CREATE_USER_TEMPLATE.getValue());

        Map model = new HashMap();
        model.put("name", event.getUsername());
        model.put("location", "Tokyo");
        model.put("loginPage", "https://github.com/chengqiangliu/benben-project");
        mail.setModel(model);

        try {
            this.mailSender.sendEmail(mail);
            Loggers.WORKER_LOGGER.info("Send email is done");
        } catch (MessagingException | IOException | TemplateException e) {
            Loggers.WORKER_LOGGER.info(LoggerFormat.COMMON_EXCEPTION, Loggers.getRootCause(e));
        }
    }
}
