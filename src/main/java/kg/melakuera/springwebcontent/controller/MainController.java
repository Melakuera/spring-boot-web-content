package kg.melakuera.springwebcontent.controller;

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
	
	private final MessageService messageService;
	
	@Autowired
	public MainController(MessageService messageService) {
		super();
		this.messageService = messageService;
	}

	@GetMapping("/greeting")
	public String greeting(
			@RequestParam(name="name", required=false, defaultValue="World" ) String name, 
			Model model) {
		model.addAttribute("name", name);
		return "greeting";
	}
	
	@GetMapping("/messages")
	public String findAll(Model model){
		model.addAttribute("messages", messageService.findAll());
		return "messages";
	}
	
	@GetMapping("/messages/new")
	public String newMessage(@ModelAttribute("message") Message message){
		return "newMessage";
	}
	
	@PostMapping("/messages")
	public String saveMessage(@ModelAttribute("message") Message message) {
		messageService.save(message);
		return "redirect:messages";
	}
}
