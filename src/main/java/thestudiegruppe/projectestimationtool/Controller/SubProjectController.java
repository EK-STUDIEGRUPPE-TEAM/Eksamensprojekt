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

    @GetMapping
    public List<SubProject> getAllSubProjects() {
        return subProjectService.getAllSubProjects();

    }

    @PostMapping("/add")
    public SubProject createSubProject(@RequestBody SubProject subProject) {
        subProjectService.createSubProject(subProject);
        return subProject;
    }

    @PostMapping("/update/{id}")
    public SubProject update(@PathVariable int id, @RequestBody SubProject subProject){
        subProjectService.updateSubProject(subProject);
        return subProject;
    }



    @DeleteMapping("/delete/{id}")
    public void deleteSubProject(@PathVariable int id) {
        subProjectService.deleteSubProject(id);
    }
}
