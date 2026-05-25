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

    @GetMapping("/addsubtask/{taskId}")
    public String addSubTask(@PathVariable int taskId, Model model, HttpSession session){

        /* Vi bruger SessionHelper til at tjekke om brugeren er logget ind.
          Hvis der ikke findes et userId i sessionen,
          sendes brugeren tilbage til login-siden */
        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        // Opretter tomt subtask objekt
        SubTask subTask = new SubTask();

        // setter task id fra vores PathVariable til objektet
        subTask.setTaskId(taskId);

        // Adder det tomme subtask objekt til formlen
        model.addAttribute("subtask", subTask);

        // adder task id til formlen
        model.addAttribute("taskId", taskId);

        // returnerer addsubtask templaten
        return "addsubtask";
    }

    @PostMapping("/save")
    // Modtager data fra formularen og opretter en ny subtask
    public String saveSubTask(@ModelAttribute SubTask subTask, HttpSession session) {

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
           til task siden */
        return "redirect:/task/" + subTask.getTaskId();
    }

    @GetMapping("/update/{taskId}/{id}")
    public String updateSubTask(@PathVariable int taskId, @PathVariable int id, Model model, HttpSession session) {
        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        SubTask subTask = subTaskService.getSubTaskById(id);
        model.addAttribute("subtask", subTask);

        model.addAttribute("taskId", taskId);
        return "updatesubtask";
    }

    @PostMapping("/saveUpdate")
    public String saveUpdate(@ModelAttribute SubTask subTask, HttpSession session) {
        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        subTaskService.updateSubTask(subTask);

        return "redirect:/task/" + subTask.getTaskId();
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
        return "redirect:/task/" + taskId;
    }

    /* Vi bruger session til at beskytte subtask-funktionerne.
   Kun brugere der er logget ind, har et userId i sessionen.
   Hvis userId ikke findes, sendes brugeren tilbage til login-siden. */
}
