package thestudiegruppe.projectestimationtool.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import thestudiegruppe.projectestimationtool.Model.Project;
import thestudiegruppe.projectestimationtool.Service.ProjectService;


@Controller

@RequestMapping("/project")

public class ProjectController {


    private final ProjectService projectService;


    public ProjectController(ProjectService projectService){

        this.projectService = projectService;
    }

    @GetMapping("/show")
    public String show(Model model){


        return null;
    }

    @GetMapping("/add")
    public String add(Model model){

        return null;

    }

    @GetMapping("/delete")
    public String delete(int id){

        return null;
    }

    @PostMapping("/save")
    public String save(Project project){

        return null;
    }

    @PostMapping("/update")
    public String update(Project project){

        return null;
    }










}
