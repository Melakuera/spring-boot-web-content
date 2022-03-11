package kg.melakuera.springwebcontent.util;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
@AllArgsConstructor
@Log
public class AppMailSender {

    private JavaMailSender mailSender;

    public void send(String to, String firstName, String lastName, String code) {
        try {
            MimeMessage mailMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true);
            helper.setTo(to);
            helper.setSubject("Код активации");
            helper.setText(String.format(
                    "<h1> Код активации </h1>" +
                    "<p> Уважаемый %s %s! " +
                    "Пожалуйста перейдите по <a href='http://localhost:8080/confirm?code=%s' >этой </a> ссылке </p>",
                    lastName, firstName, code), true);
            // Вложение не видно в одноразовых почтах
            helper.addAttachment("pistol_mem.jpg", new ClassPathResource("static/img/pistol_mem.jpg"));

            mailSender.send(mailMessage);
        } catch (MessagingException e) {
            e.getCause();
            log.info("Ошибка отправки сообщения");
        }
    }
}
