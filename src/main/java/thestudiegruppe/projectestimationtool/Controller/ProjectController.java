package thestudiegruppe.projectestimationtool.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import thestudiegruppe.projectestimationtool.Model.Project;
import thestudiegruppe.projectestimationtool.Service.ProjectService;


@RestController
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;


    public ProjectController(ProjectService projectService) {

        this.projectService = projectService;
    }

    @GetMapping("/show")
    public Project show(Project project) {
      projectService.findAllProjects();
      return project;
    }

    @GetMapping("/add")
    public Project add(Project project) {
        projectService.createProject(project);

        return project;
    }

    @GetMapping("/delete")
    public String delete(int id) {

        return null;
    }

    @PostMapping("/save")
    public String save(Project project) {



        return null;
    }

    @PostMapping("/update")
    public String update(Project project) {

        return null;
    }


}
