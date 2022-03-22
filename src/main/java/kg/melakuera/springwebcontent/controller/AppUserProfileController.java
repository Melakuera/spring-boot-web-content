package kg.melakuera.springwebcontent.controller;

import kg.melakuera.springwebcontent.entity.AppUser;
import kg.melakuera.springwebcontent.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class AppUserProfileController {
	
	private final AppUserService appUserService;
	
	@GetMapping("/profile/{id}")
	public String getUser(
			@AuthenticationPrincipal AppUser authUser,
			@PathVariable("id") Long id,
			Model model) {
		model.addAttribute("foundUser", appUserService.findById(id));
		model.addAttribute("user", authUser);
		
		return "profile";
	}
	@GetMapping("/settings/{id}")
	public String settingsUser(
			@AuthenticationPrincipal AppUser authUser,
			@PathVariable("id") Long id,
			Model model) {
		model.addAttribute("user", authUser);
		model.addAttribute("appUser", appUserService.findById(id));

		return "settings";
	}

	@PostMapping("/settings/{id}")
	public String patchUser(
			@AuthenticationPrincipal AppUser authUser,
			@PathVariable("id") Long id,
			@ModelAttribute("appUser") @Valid AppUser appUser,
			BindingResult bindingResult,
			Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("user", authUser);

			return "settings";
		}
		appUserService.update(appUser, id);

		return String.format("redirect:/profile/%s",appUser.getId());
	}

	@PostMapping("/reset/password/{id}")
	public String resetPassword(@PathVariable Long id) {
		appUserService.resetPassword(id);

		return "redirect:/logout";
	}

	@GetMapping("/update/password")
	public String updatePasswordPage(@Param("code") String code, Model model) {
		AppUser appUser = appUserService.findByResetPasswordCode(code);
		model.addAttribute("appUser", appUser);
		model.addAttribute("error_msg", false);

		return "updatePassword";
	}

	@PostMapping("/update/password/{id}")
	public String updatePassword(
			@PathVariable("id") Long id,
			@RequestParam("password1") String password1,
			@RequestParam("password2") String password2,
			Model model) {
		AppUser appUser = appUserService.findById(id);
		appUserService.updatePassword(appUser, password1, password2);
		if (!password1.equals(password2)) {
			model.addAttribute("error_msg", true);
			model.addAttribute("appUser", appUser);

			return "updatePassword";
		}
		return "redirect:/login";
	}
}
