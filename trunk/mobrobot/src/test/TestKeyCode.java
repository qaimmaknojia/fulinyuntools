package test;

import java.security.Security;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class TestKeyCode {

	public static void main(String[] args) throws Exception {
		// System.out.println(KeyEvent.VK_COLON);
		// System.out.println((int)':');
		// String mobURL = "http://mob.xiaonei.com/home.do";
		// for (int i = 0; i < mobURL.length(); i++)
		// System.out.println((int)mobURL.charAt(i));
		
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		// Get a Properties object
		Properties props = System.getProperties();
		props.setProperty("mail.smtp.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.auth", "true");
		final String username = "mobrobot";
		final String password = "qwertyui";
		Session session = Session.getDefaultInstance(props,	new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		// -- Create a new message --
		Message msg = new MimeMessage(session);

		// -- Set the FROM and TO fields --
		msg.setFrom(new InternetAddress(username + "@gmail.com"));
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(
				"fulinyun@126.com", false));
		msg.setSubject("Hello");
		
		// create and fill the first message part
		MimeBodyPart mbp1 = new MimeBodyPart();
		mbp1.setText("file transmission test");

		// create the second message part
		MimeBodyPart mbp2 = new MimeBodyPart();

		// attach the file to the message
		mbp2.attachFile("E:\\mobtemp\\test.jpg");

		// create the Multipart and add its parts to it
		Multipart mp = new MimeMultipart();
		mp.addBodyPart(mbp1);
		mp.addBodyPart(mbp2);

		// add the Multipart to the message
		msg.setContent(mp);

		msg.setSentDate(new Date());
		
		Transport.send(msg);

		System.out.println("Message sent.");
	}

}
