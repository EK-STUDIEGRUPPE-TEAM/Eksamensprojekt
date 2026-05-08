package thestudiegruppe.projectestimationtool.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import thestudiegruppe.projectestimationtool.Model.Task;
import thestudiegruppe.projectestimationtool.Service.TaskService;

@Controller
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @GetMapping("/showTask")
    public String show(Model model){
        return null;
    }

    @GetMapping("/addTask")
    public String add(Model model){
        return null;
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

