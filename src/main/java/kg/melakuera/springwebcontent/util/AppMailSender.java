package kg.melakuera.springwebcontent.util;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
@Log
public class AppMailSender {

    @Autowired
    private JavaMailSender mailSender;

    public void send(String to, String name) {
        try {
            MimeMessage mailMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true);
            helper.setTo(to);
            helper.setSubject("Код активации");
            helper.setText(String.format(
                    "<h1> Код активации </h1>" +
                    "<p> Уважаемый %s! Пожалуйста перейдите по <a href='http://localhost:8080/login'>этой</a> ссылке</p>",
            name), true);
            // Вложение не видно в одноразовых почтах
            helper.addAttachment("pistol_mem.jpg", new ClassPathResource("static/img/pistol_mem.jpg"));

            mailSender.send(mailMessage);
        } catch (MessagingException e) {
            e.getCause();
            log.info("Ошибка отправки сообщения");
        }
    }
}
