package thestudiegruppe.projectestimationtool.Controller;

import org.springframework.web.bind.annotation.*;
import thestudiegruppe.projectestimationtool.Model.SubTask;
import thestudiegruppe.projectestimationtool.Service.SubTaskService;

import java.util.List;

@RestController
@RequestMapping("/subtasks")
public class SubTaskController {

    private final SubTaskService subTaskService;

    public SubTaskController(SubTaskService subTaskService) {
        this.subTaskService = subTaskService;
    }


    @GetMapping
    public List<SubTask> getAllSubTasks() {
        return subTaskService.getAllSubTasks();
    }

    @PostMapping("/add")
    public SubTask createSubTask(@RequestBody SubTask subTask) {
        subTaskService.createSubTask(subTask);
        return subTask;
    }

    @PostMapping("/update/{id}")
    public SubTask update(@PathVariable int id, @RequestBody SubTask subTask){
        subTaskService.updateTask(subTask);
        return subTask;
    }

    @PostMapping("/delete/{id}")
    public void deleteSubTask(@PathVariable int id) {
        subTaskService.deleteSubTask(id);
    }
}
