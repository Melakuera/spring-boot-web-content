package kg.melakuera.springwebcontent.controller;

import kg.melakuera.springwebcontent.entity.AppUser;
import kg.melakuera.springwebcontent.entity.Message;
import kg.melakuera.springwebcontent.service.AppUserService;
import kg.melakuera.springwebcontent.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@AllArgsConstructor
public class MessageController {

	private MessageService messageService;
	private AppUserService appUserService;

	@GetMapping("/messages")
	public String findAll(
			Model model,
			@ModelAttribute("message") Message message,
			@AuthenticationPrincipal AppUser appUser){
		model.addAttribute("messages", messageService.findAll());
		model.addAttribute("user", appUserService.isAdmin(appUser));

		return "messages";
	}
	
	@PostMapping("/messages/new")
	public String saveMessage(
			@ModelAttribute("message") Message message,
			@AuthenticationPrincipal AppUser appUser,
			@RequestParam("file") MultipartFile file) {
		messageService.save(message, appUser, file);
		
		return "redirect:";
	}
	
	@PostMapping("/messages/filter")
	public String filterMessages(
			@RequestParam String filter,
			@ModelAttribute("message") Message message,
			@AuthenticationPrincipal AppUser appUser,
			Model model) {
		model.addAttribute("messages", messageService.findByTagLike(filter));
		model.addAttribute("user", appUserService.isAdmin(appUser));
		
		return "messages";
	}
}
