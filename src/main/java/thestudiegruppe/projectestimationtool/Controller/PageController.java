package thestudiegruppe.projectestimationtool.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import thestudiegruppe.projectestimationtool.Model.User;

@Controller
public class PageController {

    @GetMapping("/")
    public String index(){
        return "homepage";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        /* Vi laver et tomt User-objekt og sender det til HTML-siden
        Så Thymeleaf kan koble inputfelterne til user.name, user.email osv.*/
        model.addAttribute("user", new User());
        return "signUp";
    }

}
