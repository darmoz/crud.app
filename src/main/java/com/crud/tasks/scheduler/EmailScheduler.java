package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {

    private static final String SUBJECT = "Tasks: Once a day email";

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    private SimpleMailService simpleMailService;

    @Autowired
    private TaskRepository taskRepository;

    private String message;

    //@Scheduled(cron = "0 0 10 * * *")
    @Scheduled(fixedDelay = 10000)
    public void sendInformationEmail() {
        long size = taskRepository.count();
        if (size < 2) {
            message = "Currently in database you have " + size + " task";
        } else {
            message = "Currently in database you have " + size + " tasks";

        }
        simpleMailService.send(new Mail(
                adminConfig.getAdminMail(),
                SUBJECT,
                message
        ));
    }
}
