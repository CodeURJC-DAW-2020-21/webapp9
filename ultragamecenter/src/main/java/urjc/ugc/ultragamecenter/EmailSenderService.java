package urjc.ugc.ultragamecenter;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
public class EmailSenderService {

    private String from="";
    private String password="";

    public Boolean SendEmail(String to,String message,String subject){
        Properties propiedad= new Properties();
        pripiedad.setProperty("mail.smtp.host","smtp.gmail.com");
        pripiedad.setProperty("mail.smtp.starttls.enable","true");
        pripiedad.setproperty("mail.smtp.port","587");
        propiedad.setproperty("mail.smtp.auth","true");
        Session sesion= Session.getDefaultInstance(propiedad);
        MimeMessage mail = new MimeMessage(sesion);

        mail.setFrom(new InternetAdress(from));
        mail.addRecipient(Message.RecipientType.TO,new InternetAdress(to));
        mail.setSubject(subject);
        mail.setText(message);
        Transport transport= sesion.getTransport("smtp");
        transport.connecto(from,password);
        transport.sendMessage(mail,mail.getRecipients(Message.RecipientType.TO));
        transport.close();
    }
}
