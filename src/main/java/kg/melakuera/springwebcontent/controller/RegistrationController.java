package kg.melakuera.springwebcontent.controller;

import kg.melakuera.springwebcontent.entity.AppUser;
import kg.melakuera.springwebcontent.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class RegistrationController {
	
	private final RegistrationService registrationService;
	
	@GetMapping("/register")
	public String registrationPage(AppUser appUser) {
		return "register";
	}
	
	@PostMapping("/register")
	public String register(
			@ModelAttribute("appUser") @Valid AppUser appUser,
			BindingResult bindingResult,
			Model model) {

		if (bindingResult.hasErrors()) {
			return "register";
		}
		boolean result = registrationService.register(appUser);
		if (!result) {
			model.addAttribute("error_msg", !result);
			return "/register";
		}
		return "redirect:/login";
	}
}
