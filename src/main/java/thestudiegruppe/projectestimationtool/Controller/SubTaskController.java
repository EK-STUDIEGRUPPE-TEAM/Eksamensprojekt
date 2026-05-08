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

    @PostMapping
    public void createSubTask(@RequestBody SubTask subTask) {
        subTaskService.createSubTask(subTask);
    }

    @GetMapping
    public List<SubTask> getAllSubTasks() {
        return subTaskService.getAllSubTasks();

    }

    @DeleteMapping("/{id}")
    public void deleteSubTask(@PathVariable int id) {
        subTaskService.deleteSubTask(id);

    }
}
