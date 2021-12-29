package ma.gstcmd.client;

import ma.gstcmd.client.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendMail {
    @Autowired
    private JavaMailSender javaMailSender;
    public void sendMail(String email){
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("irgim.system@gmail.com");
            mailMessage.setTo(email);
            mailMessage.setSubject("Inscription Réussie");
            mailMessage.setText("Bonjour ....");
            javaMailSender.send(mailMessage);
        } catch (Exception ex){
            throw  new ClientException("erreur d'envoi d'eamil");
        }

    }
}
