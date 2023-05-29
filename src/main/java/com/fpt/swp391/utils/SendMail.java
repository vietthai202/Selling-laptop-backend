package com.fpt.swp391.utils;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@AllArgsConstructor
@NoArgsConstructor
public class SendMail {
    private JavaMailSender mailSender;

    public void SendMailController(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMailRender(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        try {
            message.setFrom("devtoolht@gmail.com");
            message.setTo(toEmail);
            message.setText(body);
            message.setSubject(subject);
            mailSender.send(message);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
