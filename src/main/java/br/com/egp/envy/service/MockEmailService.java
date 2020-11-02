package br.com.egp.envy.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;


public class MockEmailService extends AbstractEmailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MockEmailService.class);
    @Override
    public void sendEmail(SimpleMailMessage msg) {
        LOGGER.info("Simulando envio de email...");
        LOGGER.info(msg.toString());
        LOGGER.info("Email enviado");
    }

    @Override
    public void sendHtmlEmail(MimeMessage msg) {
        LOGGER.info("Simulando envio de email HTML...");
        LOGGER.info(msg.toString());
        LOGGER.info("Email enviado");
    }
}
