package stuff;

import javax.mail.*;
import javax.mail.internet.*;

import db.sql.beans.client;
import db.sql.beans.message;

import java.util.Properties;
import javax.activation.*;

public class email {
	public static boolean sendValidationEmail(String emailTarget, String emailValidationCode) {
		boolean booleanResult = false;
        
		String username = "username@mail.com";
        String password = "password";
        
		// Recipient's email ID needs to be mentioned.
		String to = emailTarget;

		// Sender's email ID needs to be mentioned
		String from = "\"511 Domains\" <username@mail.com>";

		// Get system properties
		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", "mail.com");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true"); //TLS
		
        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject: header field
			message.setSubject("Thank you for registration at 511 Domains, please verify your email address.");

			// Now set the actual message
			message.setText("Please follow this link in order to perform verification process: https://www.511domains.com/validate?" + emailValidationCode);

			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
			booleanResult = true;
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}

		return booleanResult;
	}

	public static boolean sendValidationEmailChange(String emailTarget, String emailValidationCode) {
		boolean booleanResult = false;
        
		String username = "username@mail.com";
        String password = "password";
        
		// Recipient's email ID needs to be mentioned.
		String to = emailTarget;

		// Sender's email ID needs to be mentioned
		String from = "\"511 Domains\" <username@mail.com>";

		// Get system properties
		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", "mail.com");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true"); //TLS
		
        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject: header field
			message.setSubject("Please verify your new email address.");

			// Now set the actual message
			message.setText("Please follow this link in order to perform verification process: https://www.511domains.com/validateEmailChange?" + emailValidationCode);

			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
			booleanResult = true;
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}

		return booleanResult;
	}

	public static boolean sendPasswordResetEmail(String emailTarget, String passwordResetCode) {
		boolean booleanResult = false;
        
		String username = "username@mail.com";
        String password = "password";
        
		// Recipient's email ID needs to be mentioned.
		String to = emailTarget;

		// Sender's email ID needs to be mentioned
		String from = "\"511 Domains\" <username@mail.com>";

		// Get system properties
		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", "mail.com");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true"); //TLS
		
        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject: header field
			message.setSubject("Password reset on 511 Domains.");

			// Now set the actual message
			message.setText("Please follow this link in order to perform password reset: https://www.511domains.com/resetPassword?" + passwordResetCode);

			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
			booleanResult = true;
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}

		return booleanResult;
	}

	public static boolean sendEmailMessage(message _message, client _client) {
		boolean booleanResult = false;
        
		String username = "username@mail.com";
        String password = "password";
        
		// Recipient's email ID needs to be mentioned.
		String to = "\"511 Domains\" <username@mail.com>";

		// Sender's email ID needs to be mentioned
		String from = "\"511 Domains\" <username@mail.com>";

		// Get system properties
		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", "mail.com");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true"); //TLS

		Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject: header field
			message.setSubject("Client message. Subject: " + _message.getSubject());

			// Now set the actual message
			message.setText("Message sender: " + _client.getFullName() + ", sender\'s email: " + _client.getEmail() + ".\n\nMessage text:\n===\n" + _message.getMessageText() + "\n===\n\nTime and date: " + main.dateToString(_message.getDateTimeCreated()));

			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
			booleanResult = true;
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}

		return booleanResult;
	}
}
