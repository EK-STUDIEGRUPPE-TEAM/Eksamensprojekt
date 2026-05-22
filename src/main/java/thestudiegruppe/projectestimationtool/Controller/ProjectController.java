package thestudiegruppe.projectestimationtool.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import thestudiegruppe.projectestimationtool.Model.Project;
import thestudiegruppe.projectestimationtool.Service.ProjectService;
import thestudiegruppe.projectestimationtool.sessions.SessionHelper;

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

       /* Vi bruger SessionHelper til at tjekke om brugeren er logget ind.
          Hvis der ikke findes et userId i sessionen,
          sendes brugeren tilbage til login-siden */
        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        /* Vi henter userId fra sessionen.
           userId blev gemt i sessionen, da brugeren loggede ind */
        Integer userId = SessionHelper.getLoggedInUserId(session);
        List<Project> projectList = projectService.findProjectByUserId(userId);

        /* Vi bruger userId til kun at hente de projekter,
           der tilhører den bruger, der er logget ind */
        model.addAttribute("projects" ,projectList);

        // Returnerer project.html fra templates-mappen
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
    // Viser formularen til at oprette et nyt projekt
    public String addProject(Model model, HttpSession session) {

        /* Vi bruger SessionHelper til at tjekke om brugeren er logget ind.
          Hvis der ikke findes et userId i sessionen,
          sendes brugeren tilbage til login-siden */
        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        /* Vi laver et tomt Project-objekt og sender det til HTML-siden.
           Så Thymeleaf kan koble inputfelterne til project-objektets felter */
        model.addAttribute("project", new Project());

        // Returnerer addproject.html fra templates-mappen
        return "addproject";
    }

    @PostMapping("/save")
    // Modtager data fra addproject-formularen og gemmer projektet
    public String add(@ModelAttribute Project project, HttpSession session) {

        /* Vi bruger SessionHelper til at tjekke om brugeren er logget ind.
          Hvis der ikke findes et userId i sessionen,
          sendes brugeren tilbage til login-siden */
        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        /* Vi henter userId fra sessionen.
           Det fortæller systemet hvilken bruger projektet skal tilhøre */
        Integer userId = SessionHelper.getLoggedInUserId(session);

        /* @ModelAttribute tager data fra HTML-formularen
           og lægger dem ind i et Project-objekt */

        /* Vi sender både project og userId videre til service-laget.
           På den måde bliver projektet gemt med den rigtige user_id */
        projectService.createProject(project, userId);

        // Efter projektet er oprettet, sendes brugeren tilbage til projektoversigten
        return "redirect:/projects";
    }

    @GetMapping("/delete/{id}")
    // Sletter et projekt ud fra id'et i URL'en
    public String delete(@PathVariable int id, HttpSession session) {

        /* Vi bruger SessionHelper til at tjekke om brugeren er logget ind.
          Hvis der ikke findes et userId i sessionen,
          sendes brugeren tilbage til login-siden */
        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:login";
        }

        /* Vi henter userId fra sessionen.
           Det fortæller systemet hvilken bruger er logget ind */
        int userId = SessionHelper.getLoggedInUserId(session);

        /* Vi henter projekt som objekt og tilkobler det id'et
        På den måde kan vi tjekke hvem projektet tilhører */
        Project project = projectService.findProjectById(id);

        /* Vi sammenligner projektets user id med brugeren der logget ind
        Hvis de matcher så går vi videre og sletter det
        Hvis de ikke matcher så sender vi brugeren tilbage uden at slette
         */
        if (project.getUserId() != userId) {
            return "redirect:/projects";
        }

        /* @PathVariable henter id'et fra URL'en.
           Fx /project/delete/3 betyder at id = 3 */
        projectService.deleteProject(id);

        // Efter sletning sendes brugeren tilbage til projektoversigten
        return "redirect:/projects";
    }

    @GetMapping("/update/{id}")
    public String showUpdatePage(@PathVariable int id,
                                 Model model,
                                 HttpSession session) {

        if(!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        Project project =
                projectService.findProjectById(id);

        model.addAttribute("project", project);

        return "updateproject";
    }

    @PostMapping("/update/{id}")
    // Opdaterer et projekt ud fra id'et i URL'en
    public String update(@PathVariable int id, @ModelAttribute Project project, HttpSession session) {

        /* Vi bruger SessionHelper til at tjekke om brugeren er logget ind.
          Hvis der ikke findes et userId i sessionen,
          sendes brugeren tilbage til login-siden */
        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

         /* Vi henter userId fra sessionen.
           Det sikrer at projektet stadig kobles til den bruger,
           der er logget ind */
        Integer userId = SessionHelper.getLoggedInUserId(session);


        // Hent eksisterende projekt
        Project existingProject =
                projectService.findProjectById(id);

        // Behold gamle værdier
        project.setId(id);

        project.setUserId(userId);

        project.setDate(existingProject.getDate());

        projectService.updateProject(project);

        // Efter opdatering sendes brugeren tilbage til projektoversigten
        return "redirect:/projects";
    }


    /* Lige nu bruger vi session til at sikre,
   at kun loggede brugere kan tilgå projektfunktionerne.
   Senere kan sessionens userId også bruges til at hente
   og oprette projekter for den specifikke bruger. */
}
