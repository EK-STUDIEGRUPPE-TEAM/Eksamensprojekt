package thestudiegruppe.projectestimationtool.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import thestudiegruppe.projectestimationtool.Model.Project;
import thestudiegruppe.projectestimationtool.Service.ProjectService;

import java.util.List;


@RestController
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;


    public ProjectController(ProjectService projectService) {

        this.projectService = projectService;
    }

    @GetMapping()
    public List<Project> show() {
      return projectService.findAllProjects();
    }

    @PostMapping("/add")
    public Project add(@RequestBody Project project) {
        projectService.createProject(project);
        return project;
    }

    @PostMapping("/delete/{id}")
    public Project delete(@PathVariable int id, @RequestBody Project project) {
        projectService.deleteProject(project);
        return project;
    }


    @PostMapping("/update/{id}")
    public Project update(@PathVariable int id, @RequestBody Project project) {
        projectService.updateProject(project);
        return project;
    }

//    @PostMapping("/save")
//    public String save(Project project) {
//
//        return null;
//    }

}
