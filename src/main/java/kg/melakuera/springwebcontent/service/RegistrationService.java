package kg.melakuera.springwebcontent.service;

import org.springframework.stereotype.Service;

import kg.melakuera.springwebcontent.dto.RegistrationRequestDto;
import kg.melakuera.springwebcontent.entity.AppUser;
import kg.melakuera.springwebcontent.entity.Role;
import kg.melakuera.springwebcontent.util.EmailValidator;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

@Service
@AllArgsConstructor
@Log
public class RegistrationService {
	
	private final AppUserService appUserService;
	private final EmailValidator emailValidator;

	public String register(RegistrationRequestDto request) {
		String email = request.getEmail();
		Boolean result = emailValidator.validateEmail(email);
		if (result) {
			log.info(String.format("Эл. почта %s прошла валидацию", email));
		} else {
			log.info(String.format("Эл. почта %s не прошла валидацию", email));
			return String.format("Эл. почта %s не прошла валидацию", email);
		}
		return appUserService.save(new AppUser(
				request.getFirstName(),
				request.getLastName(),
				request.getEmail(),
				request.getPassword(),
				Role.ROLE_USER,
				true,
				false
				));
	}
}
