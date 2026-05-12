package thestudiegruppe.projectestimationtool.Controller;

import org.springframework.web.bind.annotation.*;
import thestudiegruppe.projectestimationtool.Model.SubProject;
import thestudiegruppe.projectestimationtool.Service.SubProjectService;

import java.util.List;

@RestController
@RequestMapping("/subProject")
public class SubProjectController {

    private final SubProjectService subProjectService;

    public SubProjectController(SubProjectService subProjectService) {
        this.subProjectService = subProjectService;
    }

    @PostMapping
    public void createSubProject(@RequestBody SubProject subProject) {
        subProjectService.createSubProject(subProject);
    }

    @GetMapping
    public List<SubProject> getAllSubProjects() {
        return subProjectService.getAllSubProjects();

    }

    @DeleteMapping("/{id}")
    public void deleteSubProject(@PathVariable int id) {
        subProjectService.deleteSubProject(id);
    }
}
