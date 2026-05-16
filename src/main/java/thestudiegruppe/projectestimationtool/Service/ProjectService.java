package thestudiegruppe.projectestimationtool.Service;

import org.springframework.stereotype.Service;
import thestudiegruppe.projectestimationtool.Exception.NotFoundException;
import thestudiegruppe.projectestimationtool.Model.Project;
import thestudiegruppe.projectestimationtool.Model.User;
import thestudiegruppe.projectestimationtool.Repository.ProjectRepository;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;


    public ProjectService(ProjectRepository projectRepository) {

        this.projectRepository = projectRepository;
    }

    public void createProject(Project project) {
        projectRepository.add(project);
    }

    public List<Project> findProjectByUserId(int userId) {

        List<Project> projects = projectRepository.findByUserId(userId);

        if (projects.isEmpty()) {
            throw new RuntimeException("Ingen projekter tilhører bruger med id: " + userId);
        }

        return projects;
    }

    public void updateProject(Project project) {

        projectRepository.update(project);

    }

    public void deleteProject(int id) {
        projectRepository.delete(id);

    }

    public List<Project> findAllProjects() {
        return projectRepository.findAll();

    }

    public Project findProjectById(int id) {
        Project project = projectRepository.findById(id);
        if (project == null) {
            throw new NotFoundException("Projekt", id);
        }
        return project;
    }


}