package thestudiegruppe.projectestimationtool.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import thestudiegruppe.projectestimationtool.Model.SubProject;
import thestudiegruppe.projectestimationtool.Service.SubProjectService;

import java.util.List;

@Controller
@RequestMapping ("/subproject")
public class SubProjectController {

    private final SubProjectService subProjectService;

    public SubProjectController( SubProjectService subProjectService ) {
        this.subProjectService = subProjectService;
    }

    // Viser alle subprojects, der hører til et bestemt project
    @GetMapping ("/{projectId}")
    public String getSubProjectsByProjectId(@PathVariable int projectId , Model model) {

        model.addAttribute("subprojects" , subProjectService.getSubProjectsByProjectId(projectId));
        model.addAttribute("projectId", projectId);
        model.addAttribute("newSubProject", new SubProject());

        return "subproject";
    }
// Opretter et ny subproject under et bestemt project
    @PostMapping ("/add/{projectId}")
    public String createSubProject(@PathVariable int projectId, @ModelAttribute SubProject subProject) {
        subProjectService.createSubProject(projectId, subProject);

        return "redirect:/subproject/" + projectId;
    }

    @PostMapping ("/update/{projectId}/{id}")
    public String update( @PathVariable int projectId , @PathVariable int id, @ModelAttribute SubProject subProject ) {
        subProject.setId(id);
        subProjectService.updateSubProject(projectId, subProject);

        return "redirect:/subproject/" + projectId;
    }

// Sletter et subprject ud fra dets id og sender brugeres tilbage til samme project
    @PostMapping ( "/delete/{projectId}/{id}" )
    public String deleteSubProject( @PathVariable int projectId , @PathVariable int id ) {
        subProjectService.deleteSubProject(id);

        return "redirect:/subproject/" + projectId;
    }
}
