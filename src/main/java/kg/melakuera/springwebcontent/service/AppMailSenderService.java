package kg.melakuera.springwebcontent.service;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
@AllArgsConstructor
@Log
public class AppMailSenderService {

    private JavaMailSender mailSender;

    public void send(String to, String code) {
        try {
            MimeMessage mailMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true);
            helper.setTo(to);
            helper.setSubject("Код активации");
            helper.setText(String.format(
                    "<h1> Код активации </h1>" +
                    "Пожалуйста перейдите по <a href='http://localhost:8080/confirm?code=%s' >этой </a> ссылке </p>" +
                    "<img src='/img/pistol_mem.jpg' alt='kek'>",
                    code), true);
            mailSender.send(mailMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            log.info("Ошибка отправки сообщения");
        }
    }
}
