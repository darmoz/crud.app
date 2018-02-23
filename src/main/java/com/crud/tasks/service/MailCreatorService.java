package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.scheduler.EmailScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;

@Service
public class MailCreatorService {

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    private EmailScheduler emailScheduler;

    public String bulidTrelloCardEmail(String message) {

        ArrayList<String> functionality = new ArrayList<>();
        functionality.add("You can manage your task");
        functionality.add("Provides connection with Trello account");
        functionality.add("Application allows sending task to Trello");


        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("task_url", "http://localhost:8888/crud");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("goodbye", "Thank you for choosing us!");
        context.setVariable("company_name", "${info.company.name}");
        context.setVariable("company_phone", "${info.company.phone}");
        context.setVariable("company_email" , "${info.company.email}");
        context.setVariable("show_button", false);
        context.setVariable("is_friend", true);
        context.setVariable("application_functionality", functionality);
        return  templateEngine.process("mail/created-terllo-card-mail", context);

    }

    public String buildSchedulerCardEmail(String message) {

        ArrayList<String> schedulerFunctionality = new ArrayList<>();
        schedulerFunctionality.add("Sending update every 10 hours");
        schedulerFunctionality.add("Provides number of active tasks");


        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("task_url", "http://localhost:8888/crud");
        context.setVariable("button", "Check Crud App");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("goodbye", "See you in 10 hours");
        context.setVariable("company_name", "${info.company.name}");
        context.setVariable("has_tasks", emailScheduler.hasTasks());
        context.setVariable("scheduler_functionality", schedulerFunctionality);

        return templateEngine.process("mail/scheduled-email", context);
    }
}
