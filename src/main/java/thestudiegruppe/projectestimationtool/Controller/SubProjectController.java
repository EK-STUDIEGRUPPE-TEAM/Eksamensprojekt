package thestudiegruppe.projectestimationtool.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import thestudiegruppe.projectestimationtool.Model.SubProject;
import thestudiegruppe.projectestimationtool.Service.SubProjectService;

import java.util.List;

@Controller
@RequestMapping("/subproject")
public class SubProjectController {

    private final SubProjectService subProjectService;

    public SubProjectController(SubProjectService subProjectService) {
        this.subProjectService = subProjectService;
    }

    @GetMapping("/{projectId}")
    public String getSubProjectsByProjectId(@PathVariable int projectId, Model model) {
        model.addAttribute("subprojects", subProjectService.getSubProjectsByProjectId(projectId));
        return "subproject";
    }

    @PostMapping("/addSubProject")
    public String createSubProject(@ModelAttribute SubProject subProject) {
        subProjectService.createSubProject(subProject);
        return "redirect:/subproject/" + subProject.getProject().getId();
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable int id, @ModelAttribute SubProject subProject) {
        subProject.setId(id);
        subProjectService.updateSubProject(subProject);
        return "redirect:/subproject/" + subProject.getProject().getId();
    }


    @PostMapping("/delete/{projectId}/{id}")
    public String deleteSubProject(@PathVariable int projectId, @PathVariable int id) {
        subProjectService.deleteSubProject(id);
        return "redirect:/subproject/" + projectId;
    }
}
