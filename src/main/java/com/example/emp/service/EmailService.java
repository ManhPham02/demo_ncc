package com.example.emp.service;

import com.example.emp.dao.entity.User;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Locale;


public interface EmailService {
    String sendSimpleMail(String code, String emailTo);

    String sendHtmlMessage(User user,String password, Locale lang) throws MessagingException;
}
