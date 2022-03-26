package kg.melakuera.springwebcontent.service;

import kg.melakuera.springwebcontent.entity.AppUser;
import kg.melakuera.springwebcontent.entity.ConfirmationCode;
import kg.melakuera.springwebcontent.entity.Role;
import kg.melakuera.springwebcontent.repository.ConfirmationCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Log
public class RegistrationService {
	
	private final AppUserService appUserService;
	private final AppMailSenderService appMailSender;
	private final ConfirmationCodeRepository confirmationCodeRepository;

	public boolean register(AppUser formAppUser) {

		// Сохранение юзера в бд
		AppUser appUser = new AppUser(
				formAppUser.getFirstName(),
				formAppUser.getLastName(),
				formAppUser.getEmail(),
				formAppUser.getPassword(),
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
		Thread thread = new Thread(() -> appMailSender.send(formAppUser.getEmail(), code));
		thread.start();

		log.info("Письмо отправлено на почту "+ formAppUser.getEmail());
		log.info(String.format("Данный пользователь %s зарегистрирован", appUser));
		return true;
	}
}
