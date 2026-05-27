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
import thestudiegruppe.projectestimationtool.sessions.SessionHelper;

import java.time.LocalDate;

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

        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        Task task = new Task();

        task.setSubProjectId(subProjectId);

        model.addAttribute("task", task);

        model.addAttribute("projectId", projectId);

        return "addtask";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Task task, @RequestParam int projectId, HttpSession session, Model model) {

        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        if(task.getDeadline().isBefore(LocalDate.now())){

            model.addAttribute("error","Deadline må ikke være før dags dato");

            return "addtask";
        }

        taskService.createTask(task);

        return "redirect:/projects/" + projectId;
    }

    @PostMapping("/deleteTask/{subProjectId}/{id}")
    public String delete(@PathVariable int subProjectId, @PathVariable int id, HttpSession session) {

        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        // Vi har subProjectId, men vi skal bruge projectId
        SubProject subProject = subProjectService.findSubProjectById(subProjectId);

        // Derfor henter vi projectId fra subProject
        int projectId = subProject.getProjectId();

        taskService.deleteTask(id);

        return "redirect:/projects/" + projectId;
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable int id, @ModelAttribute Task task, HttpSession session, Model model) {

        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        if(task.getDeadline().isBefore(LocalDate.now())){

            task.setId(id);
            model.addAttribute("error","Deadline må ikke være før dags dato");

            return "updatetask";
        }

        task.setId(id);

        taskService.updateTask(task);

        return "redirect:/task/" + id;
    }

    @GetMapping("/update/{id}")
    public String showUpdateTask(@PathVariable int id, Model model, HttpSession session) {

        if (!SessionHelper.isLoggedIn(session)) {
            return "redirect:/login";
        }

        Task task = taskService.findTaskById(id);

        model.addAttribute("task", task);

        return "updatetask";
    }
}

