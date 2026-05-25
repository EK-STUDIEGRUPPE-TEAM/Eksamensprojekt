package thestudiegruppe.projectestimationtool.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import thestudiegruppe.projectestimationtool.Model.SubProject;
import thestudiegruppe.projectestimationtool.Service.SubProjectService;
import thestudiegruppe.projectestimationtool.sessions.SessionHelper;

@Controller
@RequestMapping("/subproject")
public class SubProjectController {

    private final SubProjectService subProjectService;

    public SubProjectController(SubProjectService subProjectService) {
        this.subProjectService = subProjectService;
    }

    @GetMapping("/addsubproject/{projectId}")
    // Viser formularen til at oprette et nyt subproject
    public String addSubProject(@PathVariable int projectId, Model model, HttpSession session) {
     /* Vi bruger SessionHelper til at tjekke om brugeren er logget ind.
          Hvis der ikke findes et userId i sessionen,
          sendes brugeren tilbage til login-siden */
        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        /* @PathVariable henter ProjectId fra URL'en.
           Det bruges til at koble den nye task til det rigtige subproject */

        // Vi laver et nyt tomt subproject objekt
        SubProject subProject = new SubProject();

        /* Vi sætter projectId på det tomme subProject-objekt.
           På den måde ved formularen, hvilket project den skal høre til */
        subProject.setProjectId(projectId);

        /* Vi sender subproject-objektet videre til HTML-siden.
           Så Thymeleaf kan koble inputfelterne til task-objektets felter */
        model.addAttribute("subproject", subProject);

        // Returnerer subproject.html fra templates-mappen
        return "addsubproject";
    }


    @PostMapping("/add/{projectId}")
    // Modtager data fra formularen og opretter et nyt delprojekt
    public String createSubProject(@PathVariable int projectId, @ModelAttribute SubProject subProject, HttpSession session) {

        /* Vi bruger SessionHelper til at tjekke om brugeren er logget ind.
          Hvis der ikke findes et userId i sessionen,
          sendes brugeren tilbage til login-siden */
        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        /* @ModelAttribute tager data fra HTML-formularen
           og lægger dem ind i et SubProject-objekt */

        /* Vi sender subProject videre til service-laget,
           som håndterer logikken for at oprette delprojektet i databasen */
        subProject.setProjectId(projectId);
        subProjectService.createSubProject(subProject);


        /* Når delprojektet er oprettet, sender vi brugeren tilbage
           til siden med delprojekter for det samme projekt */
        return "redirect:/projects/" +projectId;
    }

    @GetMapping("/update/{projectId}/{id}")
    public String updateSubProject(@PathVariable int projectId, @PathVariable int id, Model model, HttpSession session) {
        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        /* @PathVariable henter id'et fra URL'en.
           Fx /subproject/update/5 betyder at id = 5 */
        SubProject subProject = subProjectService.findSubProjectById(id);
        model.addAttribute("subproject", subProject);

        model.addAttribute("projectId", projectId);
        return "updatesubproject";
    }

    @PostMapping("/saveUpdate")
    // Opdaterer et delprojekt ud fra id'et i URL'en
    public String saveUpdate(@ModelAttribute SubProject subProject, HttpSession session) {

        /* Vi bruger SessionHelper til at tjekke om brugeren er logget ind.
          Hvis der ikke findes et userId i sessionen,
          sendes brugeren tilbage til login-siden */
        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        /* Vi sender subProject videre til service-laget,
           som håndterer opdateringen i databasen,
           kun hvis delProjektet ligger under brugerens projekt. */
        subProjectService.updateSubProject(subProject);

        /* Efter opdatering sendes brugeren tilbage
           til delprojektoversigten for samme projekt */
        return "redirect:/projects/" +subProject.getProjectId();
    }


    @PostMapping("/delete/{projectId}/{id}")
    // Sletter et delprojekt ud fra id'et i URL'en
    public String deleteSubProject(@PathVariable int projectId, @PathVariable int id, HttpSession session) {

        /* Vi bruger SessionHelper til at tjekke om brugeren er logget ind.
          Hvis der ikke findes et userId i sessionen,
          sendes brugeren tilbage til login-siden */
        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        /* @PathVariable henter både projectId og id fra URL'en.
           projectId bruges til at vide, hvilken side vi skal tilbage til.
           id bruges til at vide, hvilket delprojekt der skal slettes */

        /* Vi sender id'et videre til service-laget,
           som sletter delprojektet i databasen,
            kun hvis delProjektet tilhører brugerens projekt*/
        subProjectService.deleteSubProject(id);

        // Efter sletning sendes brugeren tilbage til samme projekts delprojekter
        return "redirect:/projects/" +projectId;
    }


    /* Vi bruger session til at sikre,
   at kun brugere der er logget ind kan se, oprette,
   opdatere og slette delprojekter.
   Hvis der ikke findes et userId i sessionen,
   bliver brugeren sendt tilbage til login-siden. */
}
