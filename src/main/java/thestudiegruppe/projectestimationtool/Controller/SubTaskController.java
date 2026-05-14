package thestudiegruppe.projectestimationtool.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import thestudiegruppe.projectestimationtool.Model.SubTask;
import thestudiegruppe.projectestimationtool.Service.SubTaskService;

@Controller
@RequestMapping("/subtask")
public class SubTaskController {

    private final SubTaskService subTaskService;

    public SubTaskController(SubTaskService subTaskService) {
        this.subTaskService = subTaskService;
    }


    @GetMapping("/{taskId}")
    public String getSubTasksByTaskId(@PathVariable int taskId, Model model) {
        model.addAttribute("subtasks", subTaskService.getSubTasksByTaskId(taskId));
        return "subtask";
    }

    @PostMapping("/addSubTask")
    public String createSubTask(@ModelAttribute SubTask subTask) {
        subTaskService.createSubTask(subTask);
        return "redirect:/subtask/" + subTask.getTaskId();
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable int id, @ModelAttribute SubTask subTask) {
        subTask.setId(id);
        subTaskService.updateSubTask(subTask);
        return "redirect:/subtask/" + subTask.getTaskId();
    }

    @PostMapping("/delete/{taskId}/{id}")
    public String deleteSubTask(@PathVariable int taskId, @PathVariable int id) {
        subTaskService.deleteSubTask(id);
        return "redirect:/subtask/" + taskId;
    }
}
