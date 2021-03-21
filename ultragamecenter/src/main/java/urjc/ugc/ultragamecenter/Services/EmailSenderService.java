package urjc.ugc.ultragamecenter.Services;

import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
	private EmailSenderService(){}
	public static Pattern isEmail = Pattern.compile("(^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$)");

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailSenderService.class);

	@Autowired
	private JavaMailSender sender;

	public void sendEmail(String email, String textMessage, String subject) {
		SimpleMailMessage send = new SimpleMailMessage();

        send.setTo(email);
        send.setSubject(subject);
        send.setText(textMessage);

        sender.send(send);
	}

}
