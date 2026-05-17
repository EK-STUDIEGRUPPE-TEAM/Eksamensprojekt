package thestudiegruppe.projectestimationtool.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import thestudiegruppe.projectestimationtool.Model.Task;
import thestudiegruppe.projectestimationtool.Service.TaskService;
import thestudiegruppe.projectestimationtool.sessions.SessionHelper;

import java.util.List;

@Controller
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{subProjectId}")
    public String getTasksBySubProjectId(@PathVariable int subProjectId, Model model, HttpSession session) {

        /* Vi bruger SessionHelper til at tjekke om brugeren er logget ind.
          Hvis der ikke findes et userId i sessionen,
          sendes brugeren tilbage til login-siden */
        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        /* @PathVariable henter subProjectId fra URL'en.
           Fx /task/2 betyder at subProjectId = 2 */

        /* Vi bruger subProjectId til at hente alle tasks,
           der hører til det valgte subproject */
        model.addAttribute("tasks", taskService.getTasksBySubProjectId(subProjectId));

        // Returnerer task.html fra templates-mappen
        return "task";
    }

    /* Vi bruger en GetMapping til addtask,
       så vi kan vise en side med formularen,
       hvor brugeren kan oprette en ny task */
    @GetMapping("/addtask/{subProjectId}")
    // Viser formularen til at oprette en ny task
    public String addTask(@PathVariable int subProjectId, Model model, HttpSession session) {

        /* Vi bruger SessionHelper til at tjekke om brugeren er logget ind.
          Hvis der ikke findes et userId i sessionen,
          sendes brugeren tilbage til login-siden */
        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        /* @PathVariable henter subProjectId fra URL'en.
           Det bruges til at koble den nye task til det rigtige subproject */

        // Vi laver et nyt tomt task objekt
        Task task = new Task();

        /* Vi sætter subProjectId på det tomme task-objekt.
           På den måde ved formularen, hvilket subproject tasken skal høre til */
        task.setSubProjectId(subProjectId);

        /* Vi sender task-objektet videre til HTML-siden.
           Så Thymeleaf kan koble inputfelterne til task-objektets felter */
        model.addAttribute("task", task);

        // Returnerer addtask.html fra templates-mappen
        return "addtask";
    }


    @PostMapping("/save")
    // Modtager data fra addtask-formularen og gemmer tasken
    public String save(@ModelAttribute Task task, HttpSession session) {

        /* Vi bruger SessionHelper til at tjekke om brugeren er logget ind.
          Hvis der ikke findes et userId i sessionen,
          sendes brugeren tilbage til login-siden */
        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        /* @ModelAttribute tager data fra HTML-formularen
           og lægger dem ind i et Task-objekt */

        /* Vi sender task videre til service-laget,
           som håndterer logikken for at oprette tasken i databasen */
        taskService.createTask(task);

        /* Efter tasken er oprettet, sender vi brugeren tilbage
           til task-oversigten for det samme subproject */
        return "redirect:/task/" + task.getSubProjectId();
    }

    @PostMapping("/deleteTask/{subProjectId}/{id}")
    // Sletter en task ud fra id'et i URL'en
    public String delete(@PathVariable int subProjectId, @PathVariable int id, HttpSession session) {

        /* Vi bruger SessionHelper til at tjekke om brugeren er logget ind.
          Hvis der ikke findes et userId i sessionen,
          sendes brugeren tilbage til login-siden */
        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        /* @PathVariable henter både subProjectId og id fra URL'en.
           subProjectId bruges til at vide, hvilken side vi skal tilbage til.
           id bruges til at vide, hvilken task der skal slettes */

        /* Vi sender id'et videre til service-laget,
           som sletter tasken i databasen */
        taskService.deleteTask(id);

        // Efter sletning sendes brugeren tilbage til samme subprojects tasks
        return "redirect:/task/" + subProjectId;
    }

    @PostMapping("/update/{id}")
    // Opdaterer en task ud fra id'et i URL'en
    public String update(@PathVariable int id, @ModelAttribute Task task, HttpSession session) {

        /* Vi bruger SessionHelper til at tjekke om brugeren er logget ind.
          Hvis der ikke findes et userId i sessionen,
          sendes brugeren tilbage til login-siden */
        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        /* Id'et kommer fra URL'en og ikke nødvendigvis fra formularen.
           Derfor sætter vi id'et manuelt på task-objektet */
        task.setId(id);


        /* Vi sender task videre til service-laget,
           som håndterer opdateringen i databasen */
        taskService.updateTask(task);

        /* Efter opdatering sendes brugeren tilbage
           til task-oversigten for samme subproject */
        return "redirect:/task/" + task.getSubProjectId();
    }

    /* Vi bruger session til at beskytte task-funktionerne.
   Kun brugere der er logget ind, har et userId i sessionen.
   Hvis userId ikke findes, sendes brugeren tilbage til login-siden. */
}

