package kg.melakuera.springwebcontent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component
@SpringBootApplication
public class SpringWebContentApplication {
	@Autowired
	private JavaMailSender mailSender;

	public static void main(String[] args) {
		SpringApplication.run(SpringWebContentApplication.class, args);
	}

	public void sendMessage(){
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

		simpleMailMessage.setFrom("spring@mail.com");
		simpleMailMessage.setTo("musabaeveldiar@gmail.com");
		simpleMailMessage.setSubject("Test");
		simpleMailMessage.setText("Тестируем отправку письма");
		mailSender.send(simpleMailMessage);
		System.out.println("Письмо отправлено");
	}
}
