package kg.melakuera.springwebcontent.service;

import org.springframework.stereotype.Service;

import kg.melakuera.springwebcontent.dto.RegistrationRequestDto;

import java.util.Map;


@Service
public class RegistrationService {

	public Map<String, String> register(RegistrationRequestDto request) {
			return Map.of("Эл. почта", request.getEmail(),
					"Пароль", request.getPassword(),
					"Имя", request.getFirstName(),
					"Фамилия", request.getLastName());
	}

}
