package urjc.ugc.ultragamecenter.controllers;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import urjc.ugc.ultragamecenter.services.EmailSenderService;

@Controller
public class MailController {
    @Autowired
    private ApplicationContext appContext;

    @GetMapping("/sendMail")
    public void sendMail() throws MessagingException {
        EmailSenderService emailSender = (EmailSenderService) appContext.getBean("emailSenderService");
        emailSender.sendEmail("santo2927@gmail.com", "testmail", "hola guapo");
    }
}
