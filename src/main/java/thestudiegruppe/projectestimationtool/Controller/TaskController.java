package thestudiegruppe.projectestimationtool.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import thestudiegruppe.projectestimationtool.Model.Task;
import thestudiegruppe.projectestimationtool.Service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @GetMapping()
    public List<Task> showAllTasks(){
        return taskService.getAllTasks();
    }

    @PostMapping("/addTask")
    public Task add(@RequestBody Task task){
        taskService.createTask(task);
        return task;
    }

    @PostMapping("/deleteTask/{id}")
    public Task delete(@PathVariable int id, @RequestBody Task task){
        taskService.deleteTask(task);
        return task;
    }

    // save fungerer som både update og additem
//    @PostMapping("/saveTask")
//    public String save(@ModelAttribute Task task){
//        return null;
//    }

    @PostMapping("/update/{id}")
    public Task update(@PathVariable int id, @RequestBody Task task){
        taskService.updateTask(task);
        return task;
    }

}

