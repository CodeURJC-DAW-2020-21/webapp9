package urjc.ugc.ultragamecenter;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSenderService {

    private String from = "";
    private String password = "";

    public Boolean sendEmail(String to, String message, String subject) {
        Properties propiedad = new Properties();
        propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
        propiedad.setProperty("mail.smtp.starttls.enable", "true");
        propiedad.setProperty("mail.smtp.port", "587");
        propiedad.setProperty("mail.smtp.auth", "true");
        Session sesion = Session.getDefaultInstance(propiedad);
        MimeMessage mail = new MimeMessage(sesion);
        try {
            mail.setFrom(new InternetAddress(from));
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            mail.setSubject(subject);
            mail.setText(message);
            Transport transport = sesion.getTransport("smtp");
            transport.connect(from, password);
            transport.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));
            transport.close();
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }
}
