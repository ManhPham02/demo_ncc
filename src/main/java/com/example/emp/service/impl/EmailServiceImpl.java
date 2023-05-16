package com.example.emp.service.impl;

import com.example.emp.dao.entity.User;
import com.example.emp.dto.Mail;
import com.example.emp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class EmailServiceImpl implements EmailService {
    JavaMailSenderImpl mailSender;
    Mail mail;
    @Autowired
    ResourceBundleMessageSource messageSource;
    private final SpringTemplateEngine templateEngine;

    public EmailServiceImpl(Mail mail, SpringTemplateEngine templateEngine) {
        this.mail = mail;
        this.templateEngine = templateEngine;
        this.mailSender = new JavaMailSenderImpl();
        this.mailSender.setHost(mail.getHost());
        this.mailSender.setPort(Integer.parseInt(mail.getPort()));
        this.mailSender.setUsername(mail.getUsername());
        this.mailSender.setPassword(mail.getPassword());

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");

        this.mailSender.setJavaMailProperties(properties);
    }

    @Override
    public String sendSimpleMail(String code, String emailTo) {
        // Try block to check for exceptions
        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("manhpdph15069@fpt.edu.vn");
            message.setTo(emailTo);
            if (code.length() == 4) {
                message.setSubject("Welcome to the company");
                message.setText("Your code is: " + code);
            } else {
                message.setSubject("Notification");
                message.setText(code);
            }

            mailSender.send(message);

            return "Mail Sent Successfully...";
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            e.printStackTrace();
            return "Error while Sending Mail";
        }
    }

    @Async
    @Override
    public String sendHtmlMessage(User user, String password, Locale lang) throws MessagingException {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "Manh");
        map.put("subscriptionDate", new Date());
        map.put("username","manh.pham");
        map.put("code","2222");
        map.put("password","1111");
//        Map<String, Object> map = new HashMap<>();
//        map.put("name", user.getFirstName());
//        map.put("subscriptionDate", new Date());
//        map.put("username", user.getUsername());
//        map.put("code", user.getCode());
//        map.put("password", password);
        map.put("company", messageSource.getMessage("company", null, lang));

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        Context context = new Context();
        context.setVariables(map);
        helper.setFrom("manhpdph15069@fpt.edu.vn");
        helper.setTo("manh1qn@gmail.com");
        helper.setSubject("Welcome to the company");
        String html = templateEngine.process("email.html", context);
        helper.setText(html, true);

        mailSender.send(message);
        return "Mail Sent Successfully...";
    }
}
