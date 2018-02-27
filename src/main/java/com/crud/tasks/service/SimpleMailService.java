package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.internet.MimeMessage;

@Service
public class SimpleMailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMailMessage.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailCreatorService mailCreatorService;

    public void send(final Mail mail) {

        LOGGER.info("Preparing mail context....");

        try {
            //SimpleMailMessage mailMessage = createMailMessage(mail);
            if(mail.getToCC().equals("dariusz.mozgowoj@gmail.com")) {
                LOGGER.warn("Additional recipient has not been added");
                javaMailSender.send(createMimeMessage(mail));
            } else {
                javaMailSender.send(createMimeMessage(mail));
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

    public MimeMessagePreparator createMimeMessage(final Mail mail) {
        return mimeMessage -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setTo(mail.getMailTo());
            mimeMessageHelper.setSubject(mail.getSubject());
            mimeMessageHelper.setText(mailCreatorService.bulidTrelloCardEmail(mail.getMessage()), true);
        };
    }

}
