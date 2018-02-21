package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailCreatorService {

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    @Autowired
    private AdminConfig adminConfig;

    public String bulidTrelloCardEmail(String message) {

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("task_url", "http://localhost:8888/crud");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("goodbye", "Thank you for choosing us!");
        context.setVariable("company_name", "${info.company.name}");
        context.setVariable("company_phone", "${info.company.phone}");
        context.setVariable("company_email" , "${info.company.email}");
        return  templateEngine.process("mail/created-terllo-card-mail", context);

    }
}
