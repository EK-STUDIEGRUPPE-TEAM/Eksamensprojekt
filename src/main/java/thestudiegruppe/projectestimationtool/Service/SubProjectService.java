package thestudiegruppe.projectestimationtool.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import thestudiegruppe.projectestimationtool.Model.SubProject;
import thestudiegruppe.projectestimationtool.Repository.ProjectRepository;
import thestudiegruppe.projectestimationtool.Repository.SubProjectRepository;

import java.util.List;

@Service
public class SubProjectService {

    private final SubProjectRepository subProjectRepository;


    public SubProjectService(SubProjectRepository subProjectRepository) {
        this.subProjectRepository = subProjectRepository;

    }

    public void createSubProject(SubProject subProject) {

        // Opretter et delProjekt under et projekt, som brugeren ejer.
        subProjectRepository.addSubProject(subProject);
    }

    public List<SubProject> getSubProjectsByProjectId(int projectId) {

        // Henter delprojekter, hvis brugeren ejer projektet
        return subProjectRepository.getSubProjectsByProjectId(projectId);
    }

    public List<SubProject> getAllSubProjects() {
        // Henter alle delprojekter, bruges primært til test/admin
        return subProjectRepository.getAllSubProjects();
    }

    public void updateSubProject(SubProject subProject) {

        // Opdaterer kun delprojektet, hvis det tilhører brugerens projekt
        subProjectRepository.updateSubProject(subProject);
    }

    public void deleteSubProject(int id) {
        // Sletter kun delprojektet, hvis det tilhører brugerens projekt
        subProjectRepository.deleteSubProject(id);
    }
}
