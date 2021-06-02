package com.ncit.finder.functionality;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {
	static public void send(String code,String sendTo) {
		final String username = "finderprojeckt@gmail.com";
        final String password = "teambad888";

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
            
          });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(sendTo));
            message.setSubject("Verification Code For Finder");
            message.setText("Your Verification Code is,"
                + "\n\n"+code);

            Transport.send(message);

            System.out.println("Mail Sent");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

	}

}
