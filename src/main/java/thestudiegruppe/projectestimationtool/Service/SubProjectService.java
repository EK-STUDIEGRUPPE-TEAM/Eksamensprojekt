package thestudiegruppe.projectestimationtool.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import thestudiegruppe.projectestimationtool.Exception.NotFoundException;
import thestudiegruppe.projectestimationtool.Model.Project;
import thestudiegruppe.projectestimationtool.Model.SubProject;
import thestudiegruppe.projectestimationtool.Model.Task;
import thestudiegruppe.projectestimationtool.Repository.SubProjectRepository;

import java.util.List;

@Service
public class SubProjectService {

    private final SubProjectRepository subProjectRepository;
    private final TaskService taskService;

    public SubProjectService(SubProjectRepository subProjectRepository, TaskService taskService) {
        this.subProjectRepository = subProjectRepository;
        this.taskService = taskService;
    }

    public void createSubProject(SubProject subProject) {
        subProjectRepository.addSubProject(subProject);
    }

    public List<SubProject> getSubProjectsByProjectId(int projectId) {
        return subProjectRepository.getSubProjectsByProjectId(projectId);
    }

    // Henter alle sub projects med tasks og subtasks indsat
    public List<SubProject> getFullSubProjects(int projectId) {

        // Vi henter alle subprojects det tilhører projektet
        List<SubProject> subProjects = subProjectRepository.getSubProjectsByProjectId(projectId);

        // Vi looper igennem hver subproject for at hente tasks der tilhører subprojektet
        for (SubProject subProject : subProjects) {

            // Vi bruger service metoden for at tilkoble de fulde tasks fra databasen med subtasks indsat
            List<Task> tasks = taskService.getFullTasks(subProject.getId());

            // Vi bruger setter til at indsætte task listen på vores subproject objekt
            subProject.setTasks(tasks);
        }

        // Vi returnerer subprojects med tasks og subtasks indsat på hvert subproject objekt
        return subProjects;
    }

    public SubProject findSubProjectById(int id) {

        SubProject subProject = subProjectRepository.findById(id);

        if (subProject == null) {
            throw new NotFoundException("SubProjekt", id);
        }

        return subProject;
    }

    public List<SubProject> getAllSubProjects() {
        return subProjectRepository.getAllSubProjects();
    }

    public void updateSubProject(SubProject subProject) {
        subProjectRepository.updateSubProject(subProject);
    }

    public void deleteSubProject(int id) {
        subProjectRepository.deleteSubProject(id);
    }
}
