package thestudiegruppe.projectestimationtool.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import thestudiegruppe.projectestimationtool.Model.Project;
import thestudiegruppe.projectestimationtool.Service.ProjectService;
import thestudiegruppe.projectestimationtool.sessions.SessionHelper;

import java.time.LocalDate;
import java.util.List;


@Controller
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    // Viser alle projekter på project-siden
    public String showProjects(Model model, HttpSession session) {

        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        /* Vi henter userId fra sessionen userId blev gemt i sessionen, da brugeren loggede ind */
        Integer userId = SessionHelper.getLoggedInUserId(session);
        List<Project> projectList = projectService.findProjectByUserId(userId);

        model.addAttribute("projects" ,projectList);

        return "projects";
    }


    @GetMapping("/{id}")
    public String showProject(Model model, @PathVariable Integer id, HttpSession session) {

        if(!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        Integer userId = SessionHelper.getLoggedInUserId(session);

        Project project = projectService.findFullProject(id);
        // Her kalder vi summary-metoderne fra ProjectService
        double totalEstimatedHours = projectService.getTotalEstimatedHoursOfWholeProject(id);
        double totalPrice = projectService.getTotalPriceOfWholeProject(id);

        // Så finder vi differencen
        double difference = projectService.getProjectDifference(id);

        //Her tjekker vi om projektet tilhører brugeren
        if(project.getUserId() != userId){
            return "redirect:/projects";
        }

        model.addAttribute("project", project);
        // Her sender vi projektet og summary-tallene videre til HTML-siden
        model.addAttribute("totalEstimatedHours", totalEstimatedHours);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("difference", difference);
        return "project";

    }

    @GetMapping("/addproject")
    public String addProject(Model model, HttpSession session) {

        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        model.addAttribute("project", new Project());

        return "addproject";
    }

    @PostMapping("/save")
    public String add(@ModelAttribute Project project, HttpSession session, Model model) {

        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        if(project.getDeadline().isBefore(LocalDate.now())){

            model.addAttribute("error","Deadline må ikke være før dags dato");

            return "addproject";
        }

        Integer userId = SessionHelper.getLoggedInUserId(session);

        projectService.createProject(project, userId);

        return "redirect:/projects";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id, HttpSession session) {

        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:login";
        }

        Integer userId = SessionHelper.getLoggedInUserId(session);

        Project project = projectService.findProjectById(id);

        if (project.getUserId() != userId) {
            return "redirect:/projects";
        }

        /* @PathVariable henter id'et fra URL'en.
           Fx /project/delete/3 betyder at id = 3 */
        projectService.deleteProject(id);

        return "redirect:/projects";
    }

    @GetMapping("/update/{id}")
    public String showUpdatePage(@PathVariable int id, Model model, HttpSession session, @RequestParam(required = false, defaultValue = "") String URL) {

        if(!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        Project project =
                projectService.findProjectById(id);

        model.addAttribute("project", project);

        model.addAttribute("URL", URL);
        return "updateproject";
    }

    // Vi bruger required = false ved requestparam så spring ikke crasher hvis der ikke er noget URL parameter
    @PostMapping("/update/{id}")
    // Opdaterer et projekt ud fra id'et i URL'en
    public String update(@PathVariable int id, @ModelAttribute Project project, HttpSession session, @RequestParam(required = false, defaultValue = "") String URL, Model model) {

        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        if(project.getDeadline().isBefore(LocalDate.now())){
            project.setId(id);

            model.addAttribute("error","Deadline må ikke være før dags dato");

            return "updateproject";
        }

        Integer userId = SessionHelper.getLoggedInUserId(session);


        Project existingProject =
                projectService.findProjectById(id);

        project.setId(id);

        project.setUserId(userId);

        project.setDate(existingProject.getDate());

        projectService.updateProject(project);

        if (URL.equals("/projects")){
            return "redirect:/projects";
        } else {
            return "redirect:/projects/" + id;
        }
    }
}
