package misha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import javax.mail.internet.MimeMessage;

@Service
public class MaleSenderService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleEmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("mihailnadia27@gmail.com");
        message.setTo("mikhaily0@mail.ru");
        message.setText("new massage 01/03/2024");

        mailSender.send(message);



    }

}




