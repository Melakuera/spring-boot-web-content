package kg.melakuera.springwebcontent.controller;

import kg.melakuera.springwebcontent.entity.AppUser;
import kg.melakuera.springwebcontent.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class AppUserProfileController {
	
	private final AppUserService appUserService;
	
	@GetMapping("/profile/{email}")
	public String getUser(
			@AuthenticationPrincipal AppUser appUser,
			@PathVariable("email") String email,
			Model model) {
		model.addAttribute("foundUser", appUserService.findByEmail(email));
		model.addAttribute("user", appUser);
		
		return "profile";
	}
	@GetMapping("/settings/{email}")
	public String settingsUser(
			@PathVariable("email") String email,
			Model model) {
		model.addAttribute("appUser", appUserService.findByEmail(email));

		return "settings";
	}

	@PostMapping("/settings/{id}")
	public String patchUser(
			@PathVariable("id") Long id,
			@ModelAttribute("appUser") @Valid AppUser appUser,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {

			return "settings";
		}
		appUserService.update(appUser, id);

		return String.format("redirect:/profile/%s",appUser.getEmail());
	}

}
