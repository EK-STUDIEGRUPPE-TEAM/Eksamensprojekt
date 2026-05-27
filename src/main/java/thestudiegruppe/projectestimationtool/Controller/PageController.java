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
    public String dashboard(Model model, HttpSession session){

        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        Integer userId = SessionHelper.getLoggedInUserId(session);

        List<Project> projects = projectService.findProjectByUserId(userId);

        //Vi adder det her for at hente userens navn selv efter redigering af profil.
        User user = userService.findUserById(userId);


        model.addAttribute("userName", user.getName());
        model.addAttribute("projects", projects);
        model.addAttribute("totalprojects", projects.size());
        model.addAttribute("completedprojects", projectService.projectsWithStatusCount(userId, Status.DONE));
        model.addAttribute("activeprojects", projectService.projectsWithStatusCount(userId, Status.IN_PROGRESS));
        return "dashboard";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }


}
