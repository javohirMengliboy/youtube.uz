package com.example.service;


import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    String fromEmail;
   /* @Value("${server.url}")
    private String serverUrl;*/


    void sendEmail(String toAccount, String subject, String text) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(fromEmail);
        msg.setTo(toAccount);
        msg.setSubject(subject);
        msg.setText(text);
        javaMailSender.send(msg);
    }

    void sendMimeEmail(String toAccount, String subject, String text) {
        try {
            jakarta.mail.internet.MimeMessage msg = javaMailSender.createMimeMessage();
            msg.setFrom(fromEmail);
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setTo(toAccount);
            helper.setSubject(subject);
            helper.setText(text, true);
            javaMailSender.send(msg);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
