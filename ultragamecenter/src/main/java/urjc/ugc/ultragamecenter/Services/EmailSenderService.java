package urjc.ugc.ultragamecenter.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


@Service("emailSenderService")
public class EmailSenderService {
    //Importante hacer la inyección de dependencia de JavaMailSender:
    @Autowired
    JavaMailSender mailSender;

    //Pasamos por parametro: destinatario, asunto y el mensaje
    public void sendEmail(String to, String code) {
        SimpleMailMessage email = new SimpleMailMessage();
        String subject = "UltraGamingCenter: código de reserva de mesa";
        String content = "Tu código de reserva de mesa es "+ code;
        email.setTo(to);
        email.setSubject(subject);
        email.setText(content);

        mailSender.send(email);
    }


}
