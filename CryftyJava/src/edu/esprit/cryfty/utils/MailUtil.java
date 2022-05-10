package edu.esprit.cryfty.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtil {
    public static void sendMail(String walletId) throws MessagingException {
        System.out.println("Preparing to Send Email");
        Properties properties = new Properties();
        properties.put("mail.smtp.auth",true);
        properties.put("mail.smtp.starttls.enable",true);
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");

        String emailAccount = "khalilrezgui0@gmail.com";
        String password = "jllaufyhdyyvabgx";
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailAccount,password);
            }
        });

        Message message = prepareMessage(session,emailAccount,"khalilrezgui1607@gmail.com",walletId);
        Transport.send(message);
        System.out.println("Email Sent");
    }

    private static Message prepareMessage(Session session,String emailAccount,String recipientEmail,String walletId) {
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(emailAccount));
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(recipientEmail));
            message.setSubject("Cryfty Support");
            message.setContent("Hey There, \n Validate your Wallet now to use it ! <a href=\"http://127.0.0.1:8000/wallet/activateWallet/"+walletId+"\"> Click Here</a>","text/html");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return message;
    }
}
