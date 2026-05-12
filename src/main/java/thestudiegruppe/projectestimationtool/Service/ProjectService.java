package thestudiegruppe.projectestimationtool.Service;

import org.springframework.stereotype.Service;
import thestudiegruppe.projectestimationtool.Model.Project;
import thestudiegruppe.projectestimationtool.Model.User;
import thestudiegruppe.projectestimationtool.Repository.ProjectRepository;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;


    public ProjectService(ProjectRepository projectRepository){

        this.projectRepository = projectRepository;
    }

    public void createProject(Project project){

        projectRepository.add(project);

    }

    public List<Project> findProjectByUser(User user){

        return projectRepository.findByUser(user);
    }

    public void updateProject(Project project){

    projectRepository.update(project);

    }
    public void deleteProject(Project project){
        projectRepository.delete(project.getId());

    }

    public List<Project> findAllProjects(){

        return projectRepository.findAll();
    }

    public Project findProjectById(int id) {

        return projectRepository.findById(id);
    }

    public List<Project> findAllProjectsByUserId(User user){
        return projectRepository.findByUser(user);
    }


}
