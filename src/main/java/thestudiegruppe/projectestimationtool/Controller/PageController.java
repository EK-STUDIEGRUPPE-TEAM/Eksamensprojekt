package thestudiegruppe.projectestimationtool.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import thestudiegruppe.projectestimationtool.Model.Project;
import thestudiegruppe.projectestimationtool.Model.Status;
import thestudiegruppe.projectestimationtool.Model.User;
import thestudiegruppe.projectestimationtool.Service.ProjectService;
import thestudiegruppe.projectestimationtool.Service.UserService;
import thestudiegruppe.projectestimationtool.sessions.SessionHelper;

import java.util.List;

@Controller
public class PageController {

    private final ProjectService projectService;

    public PageController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session){

        /* Vi bruger SessionHelper til at tjekke om brugeren er logget ind.
           Hvis der ikke findes et userId i sessionen,
           sendes brugeren tilbage til login-siden */
        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }
        /* Vi henter userId fra sessionen.
           userId blev gemt i sessionen, da brugeren loggede ind */
        Integer userId = SessionHelper.getLoggedInUserId(session);

        // Vi tilkobler projects der tilhører brugerens id til projects variablen
        List<Project> projects = projectService.findProjectByUserId(userId);

        /* Hvis brugeren er logget ind så henter vi */
        model.addAttribute("projects", projects);
        model.addAttribute("totalprojects", projects.size());
        model.addAttribute("completedprojects", projectService.projectsWithStatusCount(userId, Status.DONE));
        model.addAttribute("activeprojects", projectService.projectsWithStatusCount(userId, Status.IN_PROGRESS));
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
