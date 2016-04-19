package cat.udl.eps.entsoftarch.thesismarket.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
/**
 * Created by http://rhizomik.net/~roberto/
 */
@Service
public class MailService {
    private static final Logger logger = LoggerFactory.getLogger(MailService.class);

    @Autowired public JavaMailSender javaMailSender;

    public void sendMessage(String to, String subject, String message){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        try {
            javaMailSender.send(mailMessage);
        } catch(Exception e) {
            logger.error(e.getMessage());
        }
    }
}
