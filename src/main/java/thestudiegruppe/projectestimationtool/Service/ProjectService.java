package thestudiegruppe.projectestimationtool.Service;

import thestudiegruppe.projectestimationtool.Model.Project;
import thestudiegruppe.projectestimationtool.Model.User;
import thestudiegruppe.projectestimationtool.Repository.ProjectRepository;

import java.util.List;

public class ProjectService {

    private final ProjectRepository projectRepository;


    public ProjectService(ProjectRepository projectRepository){

        this.projectRepository = projectRepository;
    }

    public void createProject(Project project){


    }

    public List<Project> findProjectByUser(User user){

        return null;
    }

    public void updateProject(Project project){


    }

    public void deleteProject(Project project){


    }


}
