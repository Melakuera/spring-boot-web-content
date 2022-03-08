package kg.melakuera.springwebcontent.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kg.melakuera.springwebcontent.entity.ConfirmationEmail;
import kg.melakuera.springwebcontent.repository.ConfirmationEmailRepository;
import lombok.AllArgsConstructor;

//TODO Доработать

@Service
@Transactional
@AllArgsConstructor
public class ConfirmationEmailService {
	
	private ConfirmationEmailRepository confirmationTokenRepository;
	private JavaMailSender mailSender;
	
	public void save(ConfirmationEmail token) {
		confirmationTokenRepository.save(token);
	}
	
	// TODO Отправка письма в укразанный email 
	
	@Async
	public void send(String email, 	String to) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
			mimeMessageHelper.setText(email, true);
			mimeMessageHelper.setTo(to);
			mimeMessageHelper.setSubject("Подвердить свою эл. почту");
			mimeMessageHelper.setFrom("spring@mail.com");
		} catch (MessagingException e) {
			throw new IllegalStateException(String.format("Ошибка при отправке в данную %s эл. почту", email));
		}
	}	
}
