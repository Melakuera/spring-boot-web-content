package kg.melakuera.springwebcontent.controller;

import kg.melakuera.springwebcontent.dto.RegistrationRequestDto;
import kg.melakuera.springwebcontent.service.RegistrationService;
import kg.melakuera.springwebcontent.util.AppMailSender;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class RegistrationController {
	
	private final RegistrationService registrationService;
	
	@GetMapping("/register")
	public String registrationPage() {
		return "register";
	}
	
	@PostMapping("/register")
	public String register(RegistrationRequestDto request, Model model) {
		boolean result = registrationService.register(request);
		if (!result) {
			model.addAttribute("error_msg", !result);
			return "/register";
		}
		return "redirect:/login";
	}
}
