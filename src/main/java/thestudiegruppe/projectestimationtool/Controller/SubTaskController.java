package thestudiegruppe.projectestimationtool.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import thestudiegruppe.projectestimationtool.Model.SubTask;
import thestudiegruppe.projectestimationtool.Service.SubTaskService;
import thestudiegruppe.projectestimationtool.sessions.SessionHelper;

@Controller
@RequestMapping("/subtask")
public class SubTaskController {

    private final SubTaskService subTaskService;

    public SubTaskController(SubTaskService subTaskService) {
        this.subTaskService = subTaskService;
    }


    @GetMapping("/{taskId}")
    // Viser alle subtasks, der hører til en bestemt task
    public String getSubTasksByTaskId(@PathVariable int taskId, Model model, HttpSession session) {

        /* Vi bruger SessionHelper til at tjekke om brugeren er logget ind.
          Hvis der ikke findes et userId i sessionen,
          sendes brugeren tilbage til login-siden */
        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        /* @PathVariable henter taskId fra URL'en.
           Fx /subtask/3 betyder at taskId = 3 */

        /* Vi bruger taskId til at hente alle subtasks,
           der hører til den valgte task */
        model.addAttribute("subtasks", subTaskService.getSubTasksByTaskId(taskId));

        // Returnerer subtask.html fra templates-mappen
        return "subtask";
    }

    @PostMapping("/addSubTask")
    // Modtager data fra formularen og opretter en ny subtask
    public String createSubTask(@ModelAttribute SubTask subTask, HttpSession session) {

        /* Vi bruger SessionHelper til at tjekke om brugeren er logget ind.
          Hvis der ikke findes et userId i sessionen,
          sendes brugeren tilbage til login-siden */
        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        /* @ModelAttribute tager data fra HTML-formularen
           og lægger dem ind i et SubTask-objekt */

        /* Vi sender subTask videre til service-laget,
           som håndterer logikken for at oprette subtasken i databasen */
        subTaskService.createSubTask(subTask);

        /* Når subtasken er oprettet, sender vi brugeren tilbage
           til siden med subtasks for den samme task */
        return "redirect:/subtask/" + subTask.getTaskId();
    }

    @PostMapping("/update/{id}")
    // Opdaterer en subtask ud fra id'et i URL'en
    public String update(@PathVariable int id, @ModelAttribute SubTask subTask, HttpSession session) {

        /* Vi bruger SessionHelper til at tjekke om brugeren er logget ind.
          Hvis der ikke findes et userId i sessionen,
          sendes brugeren tilbage til login-siden */
        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        /* @PathVariable henter id'et fra URL'en.
           Fx /subtask/update/5 betyder at id = 5 */

        /* Id'et kommer fra URL'en og ikke nødvendigvis fra formularen.
           Derfor sætter vi id'et manuelt på subTask-objektet */
        subTask.setId(id);

        /* Vi sender subTask videre til service-laget,
           som håndterer opdateringen i databasen */
        subTaskService.updateSubTask(subTask);

        /* Efter opdatering sendes brugeren tilbage
           til subtask-oversigten for samme task */
        return "redirect:/subtask/" + subTask.getTaskId();
    }

    @PostMapping("/delete/{taskId}/{id}")
    // Sletter en subtask ud fra id'et i URL'en
    public String deleteSubTask(@PathVariable int taskId, @PathVariable int id, HttpSession session) {

        /* Vi bruger SessionHelper til at tjekke om brugeren er logget ind.
          Hvis der ikke findes et userId i sessionen,
          sendes brugeren tilbage til login-siden */
        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        /* @PathVariable henter både taskId og id fra URL'en.
           taskId bruges til at vide, hvilken side vi skal tilbage til.
           id bruges til at vide, hvilken subtask der skal slettes */

        /* Vi sender id'et videre til service-laget,
           som sletter subtasken i databasen */
        subTaskService.deleteSubTask(id);

        // Efter sletning sendes brugeren tilbage til samme tasks subtasks
        return "redirect:/subtask/" + taskId;
    }

    /* Vi bruger session til at beskytte subtask-funktionerne.
   Kun brugere der er logget ind, har et userId i sessionen.
   Hvis userId ikke findes, sendes brugeren tilbage til login-siden. */
}
