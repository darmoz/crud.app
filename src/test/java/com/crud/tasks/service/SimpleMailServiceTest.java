package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class SimpleMailServiceTest {

    @InjectMocks
    private SimpleMailService simpleMailService;

    @Mock
    private JavaMailSender javaMailSender;

    @Test
    public void shouldSendMail() {
        //Given
        Mail email = new Mail("test@test.com", "test", "test sending email" );

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email.getMailTo());
        message.setSubject(email.getSubject());
        message.setText(email.getMessage());
        message.setCc(email.getToCC());
        //When
        simpleMailService.send(email);

        //Then
        verify(javaMailSender, times(1)).send(message);
    }
}
