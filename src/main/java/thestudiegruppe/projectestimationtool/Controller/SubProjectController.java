package thestudiegruppe.projectestimationtool.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import thestudiegruppe.projectestimationtool.Model.SubProject;
import thestudiegruppe.projectestimationtool.Service.SubProjectService;
import thestudiegruppe.projectestimationtool.sessions.SessionHelper;

import java.util.List;

@Controller
@RequestMapping("/subproject")
public class SubProjectController {

    private final SubProjectService subProjectService;

    public SubProjectController(SubProjectService subProjectService) {
        this.subProjectService = subProjectService;
    }

    @GetMapping("/{projectId}")
    // Viser alle delprojekter, der hører til et bestemt projekt
    public String getSubProjectsByProjectId(@PathVariable int projectId, Model model, HttpSession session) {

        /* Vi bruger SessionHelper til at tjekke om brugeren er logget ind.
          Hvis der ikke findes et userId i sessionen,
          sendes brugeren tilbage til login-siden */
        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        /* @PathVariable henter projectId fra URL'en.
           Fx /subproject/2 betyder at projectId = 2 */

        // Henter kun delprojekter fra et projekt, som brugeren ejer
        model.addAttribute("subprojects", subProjectService.getSubProjectsByProjectId(projectId));

        // Returnerer subproject.html fra templates-mappen
        return "subproject";
    }

    @PostMapping("/addSubProject")
    // Modtager data fra formularen og opretter et nyt delprojekt
    public String createSubProject(@ModelAttribute SubProject subProject, HttpSession session) {

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
        subProjectService.createSubProject(subProject);

        /* Når delprojektet er oprettet, sender vi brugeren tilbage
           til siden med delprojekter for det samme projekt */
        return "redirect:/subproject/" + subProject.getProject().getId();
    }

    @PostMapping("/update/{id}")
    // Opdaterer et delprojekt ud fra id'et i URL'en
    public String update(@PathVariable int id, @ModelAttribute SubProject subProject, HttpSession session) {

        /* Vi bruger SessionHelper til at tjekke om brugeren er logget ind.
          Hvis der ikke findes et userId i sessionen,
          sendes brugeren tilbage til login-siden */
        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        /* @PathVariable henter id'et fra URL'en.
           Fx /subproject/update/5 betyder at id = 5 */

        /* Id'et kommer fra URL'en og ikke nødvendigvis fra formularen.
           Derfor sætter vi id'et manuelt på subProject-objektet */
        subProject.setId(id);

        /* Vi sender subProject videre til service-laget,
           som håndterer opdateringen i databasen,
           kun hvis delProjektet ligger under brugerens projekt. */
        subProjectService.updateSubProject(subProject);

        /* Efter opdatering sendes brugeren tilbage
           til delprojektoversigten for samme projekt */
        return "redirect:/subproject/" + subProject.getProject().getId();
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
        return "redirect:/subproject/" + projectId;
    }


    /* Vi bruger session til at sikre,
   at kun brugere der er logget ind kan se, oprette,
   opdatere og slette delprojekter.
   Hvis der ikke findes et userId i sessionen,
   bliver brugeren sendt tilbage til login-siden. */
}
