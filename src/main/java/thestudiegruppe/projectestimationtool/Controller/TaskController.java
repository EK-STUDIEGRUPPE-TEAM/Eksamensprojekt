package thestudiegruppe.projectestimationtool.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import thestudiegruppe.projectestimationtool.Model.Task;
import thestudiegruppe.projectestimationtool.Service.TaskService;

import java.util.List;

@Controller
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{subProjectId}")
    public String getSubTasksByTaskId(@PathVariable int subProjectId, Model model) {
        model.addAttribute("tasks", taskService.getTasksBySubProjectId(subProjectId));
        return "task";
    }

    /* vi bruger en GetMapping til addtask så vi kan vise en side
        hvorpå formlen der sender objektet til vores save metode er     */
    @GetMapping("/addtask/{subProjectId}")
    // vi tager subProjectId som en pathvariable for at kunne koble tasks til et subproject
    public String addTask(@PathVariable int subProjectId, Model model) {

        // Vi laver et nyt task tomt objekt
        Task task = new Task();

/*       Her bruger vi det tomme objekt til at indsætte subproject id i
         Vores subProjectId pathvariable kommer også i brug */
        task.setSubProjectId(subProjectId);

        // så adder vi objektet til en model attribute som vi kalder i formlen
        model.addAttribute("task", task);
        return "addtask";
    }

    // når brugeren trykker gem fra addtask formlen sendes det her
    @PostMapping("/save")
    public String save(@ModelAttribute Task task) {

        // vi bruger createtask metoden til at indsætte den nye task i databasen
        taskService.createTask(task);
        return "redirect:/task/" + task.getSubProjectId();
    }

    @PostMapping("/deleteTask/{subProjectId}/{id}")
    public String delete(@PathVariable int subProjectId, @PathVariable int id) {
        taskService.deleteTask(id);
        return "redirect:/task/" + subProjectId;
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable int id, @ModelAttribute Task task) {
        task.setId(id);
        taskService.updateTask(task);
        return "redirect:/task/" + task.getSubProjectId();
    }

}

