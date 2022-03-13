package kg.melakuera.springwebcontent.controller;

import kg.melakuera.springwebcontent.entity.AppUser;
import kg.melakuera.springwebcontent.entity.Message;
import kg.melakuera.springwebcontent.service.AppUserService;
import kg.melakuera.springwebcontent.service.MessageService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
@Log
public class MainController {
	
	private final MessageService messageService;
	private final AppUserService appUserService;

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
			@AuthenticationPrincipal AppUser appUser) {
		Message msg = new Message(message.getText(), message.getTag(), appUser);
		messageService.save(msg);
		return "redirect:";
	}
	
	@PostMapping("/messages/filter")
	public String filterMessages(
			@RequestParam String filter,
			@ModelAttribute("message") Message message,
			Model model) {	
		model.addAttribute("messages", messageService.findByTagLike(filter));
		if (filter != null && !filter.isEmpty()) {
			log.info(String.format("Фильтрация сообщении по тегу - %s",filter));
		}
		return "messages";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
}
