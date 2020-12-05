package com.wojtek.evento.service;

import com.wojtek.evento.exceptions.EException;
import com.wojtek.evento.model.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

    @Autowired
    private final JavaMailSender javaMailSender;


    @Async
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
            log.info("succesful sending verification email to: " + notificationEmail.getReceiver());
        } catch (MailException mex) {
            throw new EException("sending to: " + notificationEmail.getReceiver() + " ; failed, exception: " + mex);
        }
    }
}
