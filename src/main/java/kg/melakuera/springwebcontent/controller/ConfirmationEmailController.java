package kg.melakuera.springwebcontent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kg.melakuera.springwebcontent.service.AppUserService;
import kg.melakuera.springwebcontent.service.ConfirmationEmailService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class ConfirmationEmailController {
	
	private ConfirmationEmailService confirmationEmailService;
	private AppUserService appUserService;
	
	@GetMapping("/confirm")
	public String confirmPage() {
		return "emailConfirm";
	}
	
//	@PostMapping("/confirm")
//	public String confirm(@RequestParam String email) {
//		appUserService.confirm(email);
//
//		return "redirect:/login";
//	}
}
