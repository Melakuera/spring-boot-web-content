package kg.melakuera.springwebcontent.controller;

import kg.melakuera.springwebcontent.service.ConfirmationCodeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class ConfirmationCodeController {

    private ConfirmationCodeService confirmationCodeService;

    @GetMapping("/confirm")
    public String confirmationPage(@RequestParam(name = "code") String code, Model model){
        boolean result = confirmationCodeService.confirm(code);
        model.addAttribute("result_msg", result);

        return "confirmation";
    }
}
