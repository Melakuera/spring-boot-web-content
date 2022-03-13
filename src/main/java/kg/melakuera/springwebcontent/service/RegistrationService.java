package kg.melakuera.springwebcontent.service;

import kg.melakuera.springwebcontent.entity.ConfirmationCode;
import kg.melakuera.springwebcontent.repository.ConfirmationCodeRepository;
import kg.melakuera.springwebcontent.util.AppMailSender;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import kg.melakuera.springwebcontent.dto.RegistrationRequestDto;
import kg.melakuera.springwebcontent.entity.AppUser;
import kg.melakuera.springwebcontent.entity.Role;
import kg.melakuera.springwebcontent.util.AppValidator;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
@Log
public class RegistrationService {
	
	private final AppUserService appUserService;
	private final AppValidator appValidator;
	private final AppMailSender appMailSender;
	private final ConfirmationCodeRepository confirmationCodeRepository;

	public boolean 	register(RegistrationRequestDto request) {
		// Валидация
		String email = request.getEmail();
		boolean validateResult = appValidator.validateEmail(email);
		if (!validateResult) {
			log.info(String.format("Данная %s эл. почта не прошла валидацию", email));
			return false;
		}
		// Сохранение юзера в бд
		AppUser appUser = new AppUser(
				request.getFirstName(),
				request.getLastName(),
				request.getEmail(),
				request.getPassword(),
				Role.ROLE_USER,
				false
		);
		boolean saveResult = appUserService.register(appUser);
		if (!saveResult) {
			return false;
		}
		// Отправка кода подтверждение
		String code = UUID.randomUUID().toString();
		ConfirmationCode confirmationCode = new ConfirmationCode(
				code,
				LocalDateTime.now(),
				LocalDateTime.now().plusMinutes(1),
				appUser);
		confirmationCodeRepository.save(confirmationCode);
		Thread thread = new Thread(() -> appMailSender.send(request.getEmail(), code));
		thread.start();

		log.info("Письмо отправлено на почту "+ request.getEmail());
		log.info(String.format("Данный пользователь %s зарегистрирован", appUser));
		return true;
	}
}
