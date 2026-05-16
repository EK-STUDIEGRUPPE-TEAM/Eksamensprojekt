package thestudiegruppe.projectestimationtool.Service;

import org.springframework.stereotype.Service;
import thestudiegruppe.projectestimationtool.Model.Project;
import thestudiegruppe.projectestimationtool.Model.SubProject;
import thestudiegruppe.projectestimationtool.Repository.SubProjectRepository;

import java.util.List;

@Service
public class SubProjectService {

    private final SubProjectRepository subProjectRepository;
    private final ProjectService projectService;

    // Service får både SubProjectRepository og ProjectService ind gennem cunstructor injection
    public SubProjectService( SubProjectRepository subProjectRepository , ProjectService projectService ) {
        this.subProjectRepository = subProjectRepository;
        this.projectService = projectService;
    }
// Opretter et SubProject under et bestemt Project
    public void createSubProject(int projectId, SubProject subProject) {
        Project project = projectService.findProjectById(projectId);
        subProject.setProject(project);

        subProjectRepository.addSubProject(subProject);
    }
// Henter alle SubProjects, som hører til et bestemt Project
    public List<SubProject> getSubProjectsByProjectId(int projectId) {
        return subProjectRepository.getSubProjectsByProjectId(projectId);
    }
// Henter alle SubProjects i systemet
    public List<SubProject> getAllSubProjects() {
        return subProjectRepository.getAllSubProjects();
    }
// Opdaterer et SubProject undet et bestemt Porject
    public void updateSubProject(int projectId,SubProject subProject) {
        Project project = projectService.findProjectById(projectId);
        subProject.setProject(project);

        subProjectRepository.updateSubProject(subProject);
    }
// Sletter et Subproject ud fra id
    public void deleteSubProject(int id) {
        subProjectRepository.deleteSubProject(id);
    }
}
