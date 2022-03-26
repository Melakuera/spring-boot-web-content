package kg.melakuera.springwebcontent.controller;

import kg.melakuera.springwebcontent.entity.AppUser;
import kg.melakuera.springwebcontent.entity.Message;
import kg.melakuera.springwebcontent.service.MessageService;
import kg.melakuera.springwebcontent.service.PaginationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MessageController {

	private final MessageService messageService;
	private final PaginationService paginationService;

	@GetMapping("/messages")
	public String findAll(
			Model model,
			@ModelAttribute("message") Message message,
			@AuthenticationPrincipal AppUser appUser,
			@RequestParam(value = "filter", required = false) String filter,
			@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {

		int[] pages;

		if (filter != null) {
			pages = paginationService.getPagination(messageService.findByTagLike(filter, pageable));
			model.addAttribute("pages", pages);
			model.addAttribute("messages", messageService.findByTagLike(filter, pageable));
		} else {
			pages = paginationService.getPagination(messageService.findAll(pageable));
			model.addAttribute("pages", pages);
			model.addAttribute("messages", messageService.findAll(pageable));
		}
		model.addAttribute("filter", filter);
		model.addAttribute("user", appUser);

		return "messages";
	}

	@GetMapping("/like/message/{id}")
	public String like(
			@AuthenticationPrincipal AppUser authUser,
			@PathVariable("id") Long id,
			Model model) {
		model.addAttribute("user", authUser);
		messageService.like(authUser, id);

		return "redirect:/messages";
	}

	@GetMapping("/unlike/message/{id}")
	public String unlike(
			@AuthenticationPrincipal AppUser authUser,
			@PathVariable("id") Long id,
			Model model) {
		model.addAttribute("user", authUser);
		messageService.unlike(authUser, id);

		return "redirect:/messages";
	}
	
	@PostMapping("/messages/new")
	public String saveMessage(
			@AuthenticationPrincipal AppUser appUser,
			@RequestParam("file") MultipartFile file,
			@ModelAttribute("message") @Valid Message message,
			BindingResult bindingResult,
			Model model,
			@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("pages", paginationService.getPagination(messageService.findAll(pageable)));
			model.addAttribute("messages", messageService.findAll(pageable));
			model.addAttribute("user", appUser);
			
			return "messages";
		}
		messageService.save(message, appUser, file);
		
		return "redirect:";
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