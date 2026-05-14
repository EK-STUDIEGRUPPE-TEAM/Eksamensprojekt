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

    @PostMapping("/addTask")
    public String add(@ModelAttribute Task task) {
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

