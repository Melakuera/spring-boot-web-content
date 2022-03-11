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
    //http://localhost:8080/confirm?code={code}
    @GetMapping("/confirm")
    public String confirmationPage(@RequestParam(name = "code") String code, Model model){
        int result = confirmationCodeService.confirm(code);
        switch (result) {
            case 0:
                model.addAttribute("false_result_msg", true);
                model.addAttribute("true_result_msg", false);
                return "confirmationFalse";
            case 1:
                model.addAttribute("true_result_msg", true);
                model.addAttribute("false_result_msg", false);
                return "confirmationTrue";
        }
        return "#";
    }
}
