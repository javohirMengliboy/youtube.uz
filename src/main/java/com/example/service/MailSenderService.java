package com.example.service;

import com.example.dto.EmailHistoryDTO;
import com.example.util.JWTUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@Service
public class MailSenderService {

    @Autowired
    private EmailHistoryService emailHistoryService;

    private JavaMailSender javaMailSender;
    @Autowired
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Value("${server.url}")
    private String serverUrl;

    public void sendMimeEmail(String toAccount, String text){
        ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
        emailExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    MimeMessage msg = javaMailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(msg, true);
                    helper.setTo(toAccount);
                    helper.setSubject("Youtube registration compilation");
                    helper.setText("nimadur",text);
                    javaMailSender.send(msg);
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        emailExecutor.shutdown();
    }

    public void sendEmailVerification(String toAccount,String name,String id, String email) {
        String jwt = JWTUtil.encodeEmailJwt(id,email);
        String url = serverUrl+"api/v1/auth/verification/email/" + jwt;
//        EmailHistoryEntity emailHistoryEntity = new EmailHistoryEntity();
//        emailHistoryEntity.setMessage(url);
//        emailHistoryEntity.setEmail(toAccount);
//        emailHistoryService.create(emailHistoryEntity);

        String builder = String.format("<h1 style=\"text-align: center\">Hello %s</h1>", name) +
                "<p>" +
                String.format("<a href=\"%s\"> Click link to complete registration </a>", url) +
                "</p>";

        EmailHistoryDTO dto = new EmailHistoryDTO();
        dto.setTo_email(toAccount);
        dto.setMessage(url);
        dto.setTitle(builder);
        emailHistoryService.setValue(dto);

//        sendMimeEmail(toAccount, builder);
    }

    public void updateEmail(String toAccount,String name, String id) {
        String jwt = JWTUtil.encodeEmailJwt(id,toAccount);
        String url = serverUrl+"api/v1/profile/update/email/" + jwt;
        String builder = String.format("<h1 style=\"text-align: center\">Hello %s</h1>", name) +
                "<p>" +
                String.format("<a href=\"%s\"> Click link to complete registration </a>", url) +
                "</p>";

        sendMimeEmail(toAccount, builder);
    }
}
