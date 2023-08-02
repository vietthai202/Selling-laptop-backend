package com.fpt.swp391.controller;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HelloController {
	private final JavaMailSender mailSender;

	public  HelloController(JavaMailSender mailSender) {
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
