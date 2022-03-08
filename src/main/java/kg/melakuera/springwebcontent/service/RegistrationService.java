package kg.melakuera.springwebcontent.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import kg.melakuera.springwebcontent.dto.RegistrationRequestDto;
import kg.melakuera.springwebcontent.entity.AppUser;
import kg.melakuera.springwebcontent.entity.Role;
import kg.melakuera.springwebcontent.util.AppValidator;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Log
public class RegistrationService {
	
	private final AppUserService appUserService;
	private final AppValidator appValidator;

	public boolean register(RegistrationRequestDto request) {
		String email = request.getEmail();
		boolean validateResult = appValidator.validateEmail(email);
		if (!validateResult) {
			log.info(String.format("Данная %s эл. почта не прошла валидацию", email));
			return false;
		}
		AppUser appUser = new AppUser(
				request.getFirstName(),
				request.getLastName(),
				request.getEmail(),
				request.getPassword(),
				Role.ROLE_USER,
				true,
				true
		);
		boolean saveResult = appUserService.save(appUser);
		if (!saveResult) {
			return false;
		}
		log.info(String.format("Данный пользователь %s зарегистрирован", appUser));
		return true;
	}
}
