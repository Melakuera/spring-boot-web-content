package kg.melakuera.springwebcontent.controller;

import kg.melakuera.springwebcontent.entity.AppUser;
import kg.melakuera.springwebcontent.entity.Role;
import kg.melakuera.springwebcontent.service.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {

    private final AppUserService appUserService;

    @GetMapping("/users")
    public String getAll(Model model) {
        model.addAttribute("users", appUserService.findAll());

        return "users";
    }

    @GetMapping("/user/{id}")
    public String getOne(Model model, @PathVariable Long id) {
        model.addAttribute("user", appUserService.findById(id));
        model.addAttribute("roles", Role.values());

        return "userEdit";
    }

    @PostMapping("/user/{id}")
    public String update(@ModelAttribute("user") AppUser appUser, @PathVariable Long id) {
        appUserService.updateByAdmin(appUser, id);

        return "redirect:/users";
    }
}