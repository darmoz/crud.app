package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class SimpleMailServiceTest {

    @InjectMocks
    private SimpleMailService simpleMailService;

    @InjectMocks
    private MailCreatorService mailCreatorService;

    @Mock
    private JavaMailSender javaMailSender;

    @Test
    public void shouldSendMail() {
        //Given
        Mail email = new Mail("test@test.com", "test", "test sending email" );
        MimeMessagePreparator message = simpleMailService.createMimeMessage(email);
        //When
        simpleMailService.send(email);
        //Then
        System.out.println(message);
        //verify(javaMailSender, times(1)).send(message);
    }
}
