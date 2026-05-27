package thestudiegruppe.projectestimationtool.Service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import thestudiegruppe.projectestimationtool.Exception.NegativeValueException;
import thestudiegruppe.projectestimationtool.Exception.NotFoundException;
import thestudiegruppe.projectestimationtool.Model.*;
import thestudiegruppe.projectestimationtool.Repository.ProjectRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final SubProjectService subProjectService;

    public ProjectService(ProjectRepository projectRepository, SubProjectService subProjectService) {
        this.projectRepository = projectRepository;
        this.subProjectService = subProjectService;
    }

    public void createProject(Project project, int userId) {

        project.setDate(LocalDate.now());

        project.setStatus(Status.TODO);

        // Hvis budget er minde end 0 kaster vi en custom exception
        if (project.getBudget() < 0) {
            throw new NegativeValueException("Budget");
        }

        projectRepository.add(project, userId);
    }

    public List<Project> findProjectByUserId(int userId) {
        return projectRepository.findByUserId(userId);
    }

    public void updateProject(Project project) {

        // Hvis budget er minde end 0 kaster vi en custom exception
        if (project.getBudget() < 0) {
            throw new NegativeValueException("Budget");
        }

        projectRepository.update(project);
    }

    public void deleteProject(int id) {
        projectRepository.delete(id);
    }

    public List<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    public Project findProjectById(int id) {

        try {
            return projectRepository.findById(id);

        } catch (EmptyResultDataAccessException e){

            throw new NotFoundException("Projektet", id);
        }
    }

    /*Vi bruger den her metode til at hente de relevante subprojects og tasks som vi skal bruge
      for at returnere et projekt med subprojekter og tasks til projekt.html*/
    public Project findFullProject(int id){

        Project project;

        try {
            project = projectRepository.findById(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new NotFoundException("Projektet", id);
        }
             /*
            Vi henter alle subprojects der tilhører projektet ud fra projektets id
            Service metoden returnerer fulde subproject objekter med tasks og subtasks indsat
            */
            List<SubProject> subProjects = subProjectService.getFullSubProjects(id);

            // Vi bruger setter til at indsætte subproject listen på project
            project.setSubProjects(subProjects);

            // Til sidst returnerer vi project med subprojects og tasks indsat
            return project;
    }

    // Tæller hvor mange projekter har en specifik status til dashboard
    public int projectsWithStatusCount(int userId, Status status){

        List<Project> projects = projectRepository.findByUserId(userId);

        int count = 0;

        for (Project project : projects){

            if (project.getStatus() == status){

                count++;
            }
        }

        return count;
    }

    public double getTotalEstimatedHoursOfWholeProject(int projectId){

        double totalEstimatedHour = 0;

        // Henter hele projektet med subprojects og tasks indsat.
        Project project = findFullProject(projectId);

        // Går igennem alle subprojects i projektet.
        for (SubProject subProject : project.getSubProjects()){

            // Går igennem alle tasks i hvert subproject.
            for (Task task : subProject.getTasks()){

                // Lægger taskens estimerede timer til den samlede total.
                totalEstimatedHour += task.getEstimatedHours();
            }
        }

        return totalEstimatedHour;
    }

    public double getTotalPriceOfWholeProject(int projectId){

        double totalPrice = 0;

        // Henter hele projektet med subprojects og tasks indsat.
        Project project = findFullProject(projectId);

        // Går igennem alle subprojects i projektet.
        for (SubProject subProject : project.getSubProjects()){

            // Går igennem alle tasks i hvert subproject.
            for (Task task : subProject.getTasks()){

                // Lægger taskens totalpris til projektets samlede pris.
                totalPrice += task.getTotalPrice();
            }
        }

        return totalPrice;
    }

    public double getProjectDifference(int projectId){

        Project project = findProjectById(projectId);

        double budget = project.getBudget();

        double totalprice = getTotalPriceOfWholeProject(projectId);

        return budget - totalprice;
    }
}
