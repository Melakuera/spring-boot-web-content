package kg.melakuera.springwebcontent.service;

import org.springframework.stereotype.Service;

import kg.melakuera.springwebcontent.dto.RegistrationRequestDto;

@Service
public class RegistrationService {

	public String register(RegistrationRequestDto request) {
		return "Работает";
	}

}
