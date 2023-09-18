package com.loan.loanapp.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

	private final JavaMailSender mailSender;

	public MailServiceImpl(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Override
	public void sendMail(String to, String subject, String message) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom("emailserviceloanapp@gmail.com");
		simpleMailMessage.setTo(to);
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(message);

		this.mailSender.send(simpleMailMessage);
		// System.out.println("email sent successfully");
	}

}
