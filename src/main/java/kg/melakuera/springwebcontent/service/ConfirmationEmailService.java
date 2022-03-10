package kg.melakuera.springwebcontent.service;

import kg.melakuera.springwebcontent.entity.ConfirmationEmail;
import kg.melakuera.springwebcontent.repository.ConfirmationEmailRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//TODO Доработать

@Service
@Transactional
@AllArgsConstructor
public class ConfirmationEmailService {
	
	private ConfirmationEmailRepository confirmationTokenRepository;
	
	public void save(ConfirmationEmail token) {
		confirmationTokenRepository.save(token);
	}
}
