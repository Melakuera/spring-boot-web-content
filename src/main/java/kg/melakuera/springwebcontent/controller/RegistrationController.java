package kg.melakuera.springwebcontent.controller;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kg.melakuera.springwebcontent.dto.RegistrationRequestDto;
import kg.melakuera.springwebcontent.service.RegistrationService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class RegistrationController {
	
	private final RegistrationService registrationService;
	
	@PostMapping("/register")
	public String register(@RequestBody RegistrationRequestDto request) {
		return registrationService.register(request);
	}

	
}
