package kg.melakuera.springwebcontent.controller;

import kg.melakuera.springwebcontent.entity.AppUser;
import kg.melakuera.springwebcontent.entity.Role;
import kg.melakuera.springwebcontent.service.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
@AllArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {

    private final AppUserService appUserService;

    @GetMapping("/users")
    public String getAll(@AuthenticationPrincipal AppUser authUser, Model model) {
        model.addAttribute("users", appUserService.findAll());
        model.addAttribute("user", authUser);

        return "users";
    }

    @GetMapping("/user/{id}")
    public String getOne(
            @AuthenticationPrincipal AppUser authUser,
            @PathVariable("id") Long id,
            Model model) {
        model.addAttribute("user", authUser);
        model.addAttribute("appUser", appUserService.findById(id));
        model.addAttribute("roles", Role.values());

        return "userEdit";
    }

    @PostMapping("/user/{id}")
    public String update(
            @AuthenticationPrincipal AppUser authUser,
            @PathVariable("id") Long id,
            @ModelAttribute("appUser") @Valid AppUser appUser,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("user", authUser);
            model.addAttribute("roles", Role.values());

            return "userEdit";
        }
        appUserService.updateByAdmin(appUser, id);

        return "redirect:/users";
    }
}