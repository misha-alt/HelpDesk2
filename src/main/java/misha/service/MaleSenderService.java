package misha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import javax.mail.internet.MimeMessage;
import java.security.Principal;

@Service
@PropertySource("classpath:application.properties")
public class MaleSenderService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private UserService userService;

    @Value("${mail.sender}")
    private String youMail;
   @Value("${mail.massage}")
    private String mailMassag;

   @Value("${mail.sendTo}")
   private String [] sendTo;

    public void sendSimpleEmail(/*String[] toWhom, String youMassage*/) {




        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(youMail);
        message.setTo(sendTo);
        message.setText(mailMassag);

        mailSender.send(message);



    }

}




