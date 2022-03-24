package kg.melakuera.springwebcontent.controller;

import kg.melakuera.springwebcontent.entity.AppUser;
import kg.melakuera.springwebcontent.entity.Message;
import kg.melakuera.springwebcontent.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class MessageController {

	private MessageService messageService;

	@GetMapping("/messages")
	public String findAll(
			Model model,
			@ModelAttribute("message") Message message,
			@AuthenticationPrincipal AppUser appUser){
		model.addAttribute("messages", messageService.findAll());
		model.addAttribute("user", appUser);

		return "messages";
	}
	
	@PostMapping("/messages/new")
	public String saveMessage(
			@AuthenticationPrincipal AppUser appUser,
			@RequestParam("file") MultipartFile file,
			@ModelAttribute("message") @Valid Message message,
			BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("messages", messageService.findAll());
			model.addAttribute("user", appUser);
			
			return "messages";
		}
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
		model.addAttribute("user", appUser);
		
		return "messages";
	}

	@DeleteMapping("/delete/message/{id}")
	public String delete(@PathVariable("id") Long id) {
		messageService.deleteById(id);

		return "redirect:/messages";
	}

	@GetMapping("/update/message/{id}")
	public String updatePage(
			@AuthenticationPrincipal AppUser appUser,
			@PathVariable("id") Long id,
			Model model) {
		model.addAttribute("user", appUser);
		model.addAttribute("message", messageService.findById(id));

		return "messageEdit";
	}

	@PatchMapping("/update/message/{id}")
	public String update(
			@AuthenticationPrincipal AppUser appUser,
			@RequestParam("file") MultipartFile file,
			@PathVariable("id") Long id,
			@ModelAttribute("message") @Valid Message message,
			BindingResult bindingResult,
			Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("user", appUser);
			return "messageEdit";
		}
		messageService.update(message, file, id);
		return "redirect:/messages";
	}
}
