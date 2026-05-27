package thestudiegruppe.projectestimationtool.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import thestudiegruppe.projectestimationtool.Model.SubProject;
import thestudiegruppe.projectestimationtool.Service.SubProjectService;
import thestudiegruppe.projectestimationtool.sessions.SessionHelper;

@Controller
@RequestMapping("/subproject")
public class SubProjectController {

    private final SubProjectService subProjectService;

    public SubProjectController(SubProjectService subProjectService) {
        this.subProjectService = subProjectService;
    }

    @GetMapping("/addsubproject/{projectId}")
    public String addSubProject(@PathVariable int projectId, Model model, HttpSession session) {

        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        SubProject subProject = new SubProject();

        subProject.setProjectId(projectId);

        model.addAttribute("subproject", subProject);

        return "addsubproject";
    }


    @PostMapping("/add/{projectId}")
    public String createSubProject(@PathVariable int projectId, @ModelAttribute SubProject subProject, HttpSession session) {

        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        subProject.setProjectId(projectId);
        subProjectService.createSubProject(subProject);

        return "redirect:/projects/" +projectId;
    }

    @GetMapping("/update/{projectId}/{id}")
    public String updateSubProject(@PathVariable int projectId, @PathVariable int id, Model model, HttpSession session) {
        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        SubProject subProject = subProjectService.findSubProjectById(id);
        model.addAttribute("subproject", subProject);

        model.addAttribute("projectId", projectId);
        return "updatesubproject";
    }

    @PostMapping("/saveUpdate")
    public String saveUpdate(@ModelAttribute SubProject subProject, HttpSession session) {

        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        subProjectService.updateSubProject(subProject);

        return "redirect:/projects/" +subProject.getProjectId();
    }


    @PostMapping("/delete/{projectId}/{id}")
    public String deleteSubProject(@PathVariable int projectId, @PathVariable int id, HttpSession session) {

        if (!SessionHelper.isLoggedIn(session)){
            return "redirect:/login";
        }

        subProjectService.deleteSubProject(id);

        return "redirect:/projects/" + projectId;
    }
}
