package com.codemock.service;

import java.util.Properties;
import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailService {

    private final String senderEmail = "codemockbymahakg@gmail.com";
    private final String senderPassword = "pezn ersf rllf hpuu";

    public boolean sendOtpEmail(String recipientEmail, String otp) {
        boolean isSent = false;

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        // 1. Simple Session without any Authenticator override
        Session session = Session.getInstance(properties);

        try {
            // 2. Draft the Message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject("Your CodeMock Verification OTP");
            
            String htmlContent = "<h2>Welcome to CodeMock!</h2>"
                    + "<p>Your 6-digit OTP for account verification is: <b>" + otp + "</b></p>"
                    + "<p>Please enter this code to activate your account. This OTP is valid for 10 minutes.</p>"
                    + "<br><p>Happy Coding,<br>The CodeMock Team</p>";
                    
            message.setContent(htmlContent, "text/html");

            // 3. Connect to Transport directly with password and send
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", senderEmail, senderPassword);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close(); // Close the connection after sending

            isSent = true;
            
        } catch (Exception e) {
            System.err.println("Error sending OTP email: " + e.getMessage());
            e.printStackTrace();
        }

        return isSent;
    }
}