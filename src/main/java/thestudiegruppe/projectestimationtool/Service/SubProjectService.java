package thestudiegruppe.projectestimationtool.Service;

import org.springframework.web.bind.annotation.*;
import thestudiegruppe.projectestimationtool.Model.SubProject;

import java.util.List;

@RestController
@RequestMapping
public class SubProjectService {

    private final SubProjectService subProjectService;

    public SubProjectService(SubProjectService subProjectService) {
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
