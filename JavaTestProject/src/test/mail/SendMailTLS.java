package test.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Important:
 * in order for this to work you need to enable access for less secure apps in this gmail account
 * @author Leo
 *
 */
public class SendMailTLS {

	final static String username = "leoio1953@gmail.com";
	final static String password = "passw0rd1953";
	
	public static void main(String[] args) {
		SendMailTLS.send("begemot57@hotmail.com", "Testing Subject", "Dear Mail Crawler,"
				+ "\n\n No spam to my email, please!");		
	}
	
	public static void send(String to, String subject, String msg){
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
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
			message.setFrom(new InternetAddress("leoio1953@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(to));
			message.setSubject(subject);
			message.setText(msg);
 
			Transport.send(message);
 
//			System.out.println("Done");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
