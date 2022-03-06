package kg.melakuera.springwebcontent.service;

import org.springframework.stereotype.Service;

import kg.melakuera.springwebcontent.dto.RegistrationRequestDto;
import kg.melakuera.springwebcontent.entity.AppUser;
import kg.melakuera.springwebcontent.entity.Role;
import kg.melakuera.springwebcontent.util.AppValidator;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegistrationService {
	
	private final AppUserService appUserService;
	private final AppValidator appValidator;

	public String register(RegistrationRequestDto request) {
		String email = request.getEmail();
		Boolean result = appValidator.validateEmail(email);
		if (!result) {
			return String.format("Эл. почта %s не прошла валидацию", email);
		}
		return appUserService.save(new AppUser(
				request.getFirstName(),
				request.getLastName(),
				request.getEmail(),
				request.getPassword(),
				Role.ROLE_USER,
				true,
				true
				));
	}
}
