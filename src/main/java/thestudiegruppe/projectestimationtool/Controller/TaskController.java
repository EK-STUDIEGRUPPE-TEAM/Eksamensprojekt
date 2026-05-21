package thestudiegruppe.projectestimationtool.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import thestudiegruppe.projectestimationtool.Model.Project;
import thestudiegruppe.projectestimationtool.Model.SubProject;
import thestudiegruppe.projectestimationtool.Model.Task;
import thestudiegruppe.projectestimationtool.Service.ProjectService;
import thestudiegruppe.projectestimationtool.Service.SubProjectService;
import thestudiegruppe.projectestimationtool.Service.TaskService;
import thestudiegruppe.projectestimationtool.Service.UserService;
import thestudiegruppe.projectestimationtool.sessions.SessionHelper;

import java.util.List;

@Controller
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;
    private final SubProjectService subProjectService;
    private final ProjectService projectService;

    public TaskController(TaskService taskService,  SubProjectService subProjectService, ProjectService projectService) {
        this.taskService = taskService;
        this.subProjectService = subProjectService;
        this.projectService = projectService;

    }

    /* Vi bruger en GetMapping til addtask,
       så vi kan vise en side med formularen,
       hvor brugeren kan oprette en ny task */



    @GetMapping("/{id}")
    public String showTask(Model model, @PathVariable Integer id, HttpSession session) {


        if(!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        Integer userId = SessionHelper.getLoggedInUserId(session);

        Task task = taskService.findFullTask(id);


        //Her tjekker vi om projektet tilhører brugeren
        if(task == null){
            return "redirect:/projects";
        }


        SubProject subProject = subProjectService.findSubProjectById(task.getSubProjectId());

        Project project = projectService.findProjectById(subProject.getProjectId());



        if(project.getUserId() != userId){

            return "redirect:/projects";
        }

        model.addAttribute("task", task);
        model.addAttribute("projectId", project.getId());
        return "task";


    }


    /* @RequestParam henter projectId fra URL'en
    så vi kan bruge det til at sende brugeren tilbage til det rigtige projekt efter save */
    @GetMapping("/addtask/{subProjectId}")
    public String addTask(@PathVariable int subProjectId, @RequestParam int projectId, Model model, HttpSession session) {

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

        /* Vi sender projectId videre til HTML-siden.
       Så vi kan sende brugeren tilbage til det rigtige projekt efter save */
        model.addAttribute("projectId", projectId);

        // Returnerer addtask.html fra templates-mappen
        return "addtask";
    }

    // Modtager data fra addtask-formularen og gemmer tasken
    @PostMapping("/save")
    public String save(@ModelAttribute Task task, @RequestParam int projectId, HttpSession session) {

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
           til project viewpointet */
        return "redirect:/projects/" + projectId;
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

        // Vi har subProjectId, men vi skal bruge projectId
        SubProject subProject = subProjectService.findSubProjectById(subProjectId);

        // Derfor henter vi projectId fra subProject
        int projectId = subProject.getProjectId();

        /* @PathVariable henter både subProjectId og id fra URL'en.
           subProjectId bruges til at vide, hvilken side vi skal tilbage til.
           id bruges til at vide, hvilken task der skal slettes */

        /* Vi sender id'et videre til service-laget,
           som sletter tasken i databasen */
        taskService.deleteTask(id);

        // Efter sletning sendes brugeren tilbage til samme subprojects tasks,
        // Nu kan vi sende brugeren tilbage til det rigtige projekt
        return "redirect:/projects/" + projectId;
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

        /* Vi redirecter tilbage til taskens eget id.
        subProjectId bruges ikke her, fordi /task/{id}
        skal modtage et task-id og ikke et subproject-id.*/
        return "redirect:/task/" + id;
    }

    @GetMapping("/update/{id}")
    public String showUpdateTask(@PathVariable int id, Model model, HttpSession session) {

        /*
    tjekker om brugeren er logget ind
    hvis ikke, sendes brugeren til login
    */
        if (!SessionHelper.isLoggedIn(session)) {
            return "redirect:/login";
        }

         /*
    finder tasken ud fra id'et
    så de gamle værdier kan vises i formularen
    */
        Task task = taskService.findTaskById(id);

         /*
    sender task objektet til html-siden
    så Thymeleaf kan bruge det i th:object
    */
        model.addAttribute("task", task);


    /*
    viser updatetask.html
    */
        return "updatetask";
    }

    /* Vi bruger session til at beskytte task-funktionerne.
   Kun brugere der er logget ind, har et userId i sessionen.
   Hvis userId ikke findes, sendes brugeren tilbage til login-siden. */
}

