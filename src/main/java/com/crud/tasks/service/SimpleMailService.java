package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class SimpleMailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMailMessage.class);

    @Autowired
    private JavaMailSender javaMailSender;

    public void send(final Mail mail) {

        LOGGER.info("Preparing mail context....");

        try {
            SimpleMailMessage mailMessage = createMailMessage(mail);
            if(mail.getToCC().equals("NA")) {
                LOGGER.info("Additional recipient has not been added");
                javaMailSender.send(mailMessage);
            } else {
                javaMailSender.send(mailMessage);
            }

            LOGGER.info("Mail has been sent");

        } catch (MailException e) {

            LOGGER.error("Failed to process mail sending!", e.getMessage(), e);

        }

    }

    private SimpleMailMessage createMailMessage(final Mail mail) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(mail.getMailTo());
            mailMessage.setSubject(mail.getSubject());
            mailMessage.setText(mail.getMessage());
            mailMessage.setCc(mail.getToCC());



        return mailMessage;
    }

}
