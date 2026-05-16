package thestudiegruppe.projectestimationtool.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import thestudiegruppe.projectestimationtool.Model.SubProject;
import thestudiegruppe.projectestimationtool.Repository.SubProjectRepository;

import java.util.List;

@Service
public class SubProjectService {

    private final SubProjectRepository subProjectRepository;

    public SubProjectService(SubProjectRepository subProjectRepository) {
        this.subProjectRepository = subProjectRepository;
    }

    public void createSubProject(SubProject subProject) {
        subProjectRepository.addSubProject(subProject);
    }

    public List<SubProject> getSubProjectsByProjectId(int projectId) {
        return subProjectRepository.getSubProjectsByProjectId(projectId);
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
