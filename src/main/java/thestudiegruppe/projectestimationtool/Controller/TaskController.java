package thestudiegruppe.projectestimationtool.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import thestudiegruppe.projectestimationtool.Model.Task;
import thestudiegruppe.projectestimationtool.Service.TaskService;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @GetMapping("/showTask")
    public String show(Model model){
        model.addAttribute("tasks", taskService.getAllTasks());
        return "showTask";
    }

    @GetMapping("/addTask")
    public String add(Model model){
        model.addAttribute("task", new Task());
        return "addTask";
    }

    @GetMapping("/deleteTask/{id}")
    public String delete(@PathVariable int id){
        return null;
    }

    // save fungerer som både update og additem
    @PostMapping("/saveTask")
    public String save(@ModelAttribute Task task){
        return null;
    }

    @GetMapping
    public String update(@PathVariable Task task){
        return null;
    }
}

