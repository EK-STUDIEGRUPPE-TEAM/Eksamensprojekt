package thestudiegruppe.projectestimationtool.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import thestudiegruppe.projectestimationtool.Model.SubTask;
import thestudiegruppe.projectestimationtool.Service.SubTaskService;
import thestudiegruppe.projectestimationtool.sessions.SessionHelper;

@Controller
@RequestMapping("/subtask")
public class SubTaskController {

    private final SubTaskService subTaskService;

    public SubTaskController(SubTaskService subTaskService) {
        this.subTaskService = subTaskService;
    }

    @GetMapping("/addsubtask/{taskId}")
    public String addSubTask(@PathVariable int taskId, Model model, HttpSession session){

        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        SubTask subTask = new SubTask();

        subTask.setTaskId(taskId);

        model.addAttribute("subtask", subTask);

        model.addAttribute("taskId", taskId);

        return "addsubtask";
    }

    @PostMapping("/save")
    public String saveSubTask(@ModelAttribute SubTask subTask, HttpSession session) {

        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        subTaskService.createSubTask(subTask);

        return "redirect:/task/" + subTask.getTaskId();
    }

    @GetMapping("/update/{taskId}/{id}")
    public String updateSubTask(@PathVariable int taskId, @PathVariable int id, Model model, HttpSession session) {
        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        SubTask subTask = subTaskService.getSubTaskById(id);
        model.addAttribute("subtask", subTask);

        model.addAttribute("taskId", taskId);
        return "updatesubtask";
    }

    @PostMapping("/saveUpdate")
    public String saveUpdate(@ModelAttribute SubTask subTask, HttpSession session) {
        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        subTaskService.updateSubTask(subTask);

        return "redirect:/task/" + subTask.getTaskId();
    }

    @PostMapping("/delete/{taskId}/{id}")
    public String deleteSubTask(@PathVariable int taskId, @PathVariable int id, HttpSession session) {

        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        subTaskService.deleteSubTask(id);

        return "redirect:/task/" + taskId;
    }
}
