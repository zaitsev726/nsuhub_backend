package ru.nsu.backendmodule.email;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.nsu.backendmodule.config.EmailProperties;

@Service
public class EmailService {
    private final JavaMailSender emailSender;
    private final EmailProperties emailProperties;

    //no need email service impl, because of properties in application.yml
    public EmailService(JavaMailSender emailSender, EmailProperties emailProperties) {
        this.emailSender = emailSender;
        this.emailProperties = emailProperties;
    }

    @Async
    public void sendSimpleMessage(String[] to, String subject, String body) {
        var simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(emailProperties.from());
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);
        emailSender.send(simpleMailMessage);
    }

    @Async
    public void sendSimpleMessage(String to, String subject, String body) {
        sendSimpleMessage(new String[]{to}, subject, body);
    }
}
