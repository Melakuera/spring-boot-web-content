package kg.melakuera.springwebcontent.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kg.melakuera.springwebcontent.dto.RegistrationRequestDto;
import kg.melakuera.springwebcontent.service.RegistrationService;
import lombok.AllArgsConstructor;

import java.util.Map;

@RestController
@AllArgsConstructor
public class RegistrationController {
	
	private final RegistrationService registrationService;
	
	@PostMapping("/register")
	public Map<String, String> register(@RequestBody RegistrationRequestDto request) {
		return registrationService.register(request);
	}

	
}
