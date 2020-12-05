package com.wojtek.evento.service;

import com.wojtek.evento.model.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@Service
@AllArgsConstructor
public class MailService {

    @Autowired
    private final JavaMailSender javaMailSender;


    public void sendMail(NotificationEmail notificationEmail) {
        try {
            MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
                messageHelper.setSubject(notificationEmail.getSubject());
                messageHelper.setText(notificationEmail.getBody());
                messageHelper.setFrom("evenTo@mail.com");
                messageHelper.setTo(notificationEmail.getReceiver());
            };
            javaMailSender.send(mimeMessagePreparator);
            System.out.println("succesful sending verification email to: " + notificationEmail.getReceiver());
        } catch (MailException mex) {
            System.out.println("sending to: " + notificationEmail.getReceiver() + " ; failed, exception: " + mex);
        }
    }
}
