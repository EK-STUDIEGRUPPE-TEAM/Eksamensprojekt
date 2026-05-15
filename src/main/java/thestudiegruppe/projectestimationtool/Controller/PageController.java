package thestudiegruppe.projectestimationtool.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import thestudiegruppe.projectestimationtool.Model.User;
import thestudiegruppe.projectestimationtool.Service.ProjectService;
import thestudiegruppe.projectestimationtool.Service.UserService;

@Controller
public class PageController {

    private final ProjectService projectService;
    private final UserService userService;

    public PageController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model){
        model.addAttribute("projects", projectService.findAllProjects());
        return "dashboard";
    }

    @GetMapping("/login")
    public String login(Model model){

        /* Vi laver et tomt User-objekt og sender det til HTML-siden
           Så Thymeleaf kan koble inputfelterne til user.email og user.password */
        model.addAttribute("user", new User());

        // Returnerer login.html fra templates-mappen
        return "login";
    }

    @GetMapping("/signup")
    public String signup(Model model) {

        /* Vi laver et tomt User-objekt og sender det til HTML-siden
        Så Thymeleaf kan koble inputfelterne til user.name, user.email osv.*/
        model.addAttribute("user", new User());
        return "signup";
    }

}
