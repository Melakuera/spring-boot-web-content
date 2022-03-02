package kg.melakuera.springwebcontent.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kg.melakuera.springwebcontent.entity.Message;
import kg.melakuera.springwebcontent.service.MessageService;

@Controller
public class MainController {
	
	private static final Logger log = Logger.getLogger(MainController.class.getName());
	
	private final MessageService messageService;
	
	@Autowired
	public MainController(MessageService messageService) {
		this.messageService = messageService;
	}
	
	@GetMapping("/messages")
	public String findAll(Model model, @ModelAttribute("message") Message message){
		model.addAttribute("messages", messageService.findAll());
		return "messages";
	}
	
	@PostMapping("/messages/new")
	public String saveMessage(@ModelAttribute("message") Message message) {
		messageService.save(message);
		return "redirect:";
	}
	
	@PostMapping("/messages/filter")
	public String filterMessages(
			@RequestParam String filter,
			@ModelAttribute("message") Message message,
			Model model) {
		
		model.addAttribute("messages", messageService.findByTagLike(filter));
		if (filter != null && !filter.isEmpty()) {
			log.info("Фильтр поиска сообщении по тегу - "+filter);
		}
		return "messages";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
}
