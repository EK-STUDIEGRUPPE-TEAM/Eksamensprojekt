package thestudiegruppe.projectestimationtool.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import thestudiegruppe.projectestimationtool.Model.Project;
import thestudiegruppe.projectestimationtool.Service.ProjectService;


@Controller
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public String showProjects(Model model) {
        model.addAttribute("projects" ,projectService.findAllProjects());
        return "project";
    }

    @GetMapping("/addproject")
    public String addProject(Model model) {
        model.addAttribute("project", new Project());
        return "addproject";
    }

    @PostMapping("/save")
    public String add(@ModelAttribute Project project) {
        projectService.createProject(project);
        return "redirect:/project";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        projectService.deleteProject(id);
        return "redirect:/project";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable int id, @ModelAttribute Project project) {
        project.setId(id);
        projectService.updateProject(project);
        return "redirect:/project";
    }


}
