package thestudiegruppe.projectestimationtool.Service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import thestudiegruppe.projectestimationtool.Exception.NotFoundException;
import thestudiegruppe.projectestimationtool.Model.Project;
import thestudiegruppe.projectestimationtool.Model.Status;
import thestudiegruppe.projectestimationtool.Model.SubProject;
import thestudiegruppe.projectestimationtool.Repository.ProjectRepository;

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

        /* Denne metode bruges til at oprette et nyt projekt.
           Vi modtager både project og userId.

           project kommer fra formularen.
           userId kommer fra sessionen og fortæller,
           hvilken bruger projektet skal tilhøre */

        /* Vi sender project og userId videre til repository-laget.
           Repository-laget står for selve SQL-koden,
           som indsætter projektet i databasen med den rigtige user_id */

        projectRepository.add(project, userId);
    }

    /*Har fjernet en exception herfra, da det ikke giver mening at det en fejl,
      hvis en bruger ikke har nogen projekter endnu.*/
    public List<Project> findProjectByUserId(int userId) {

        /* Denne metode bruges til at hente projekter,
           der tilhører den bruger, der er logget ind.

           userId kommer fra sessionen i controlleren
           og sendes videre hertil til service-laget */

        /* Vi kalder repository-laget,
           som henter alle projekter fra databasen,
           hvor user_id matcher den loggede brugers id */

        return projectRepository.findByUserId(userId);
    }

    public void updateProject(Project project) {

        /* Denne metode bruges til at opdatere et projekt.
           Project-objektet indeholder de nye værdier fra formularen */

        /* Vi sender projektet videre til repository-laget,
           som opdaterer projektet i databasen */

        projectRepository.update(project);
    }

    public void deleteProject(int id) {

        /* Denne metode bruges til at slette et projekt.
           id kommer typisk fra URL'en via @PathVariable i controlleren */

        /* Vi sender id'et videre til repository-laget,
           som sletter projektet fra databasen */

        projectRepository.delete(id);
    }

    public List<Project> findAllProjects() {

        /* Denne metode henter alle projekter fra databasen.
           Den kan bruges til test eller admin-lignende funktioner.

           I det normale bruger-flow bruger vi hellere findProjectByUserId(),
           så brugeren kun ser sine egne projekter */

        return projectRepository.findAll();
    }

    public Project findProjectById(int id) {

        /* Denne metode bruges til at finde ét bestemt projekt
           ud fra projektets id */

        /* Hvis repository ikke finder et projekt,
           kaster vi vores egen NotFoundException. */

           // Vi bruger try/catch, fordi vi først skal prøve at finde projektet i databasen.
           // Hvis databasen ikke finder et projekt, fanger catch fejlen.
        try {
            /* Hvis projektet findes, returnerer vi det */
            return projectRepository.findById(id);
        } catch (EmptyResultDataAccessException e){
            throw new NotFoundException("Projekt", id);
        }
    }

    /* Vi bruger den her metode til at hente de relevante subprojects og tasks som vi skal bruge
      for at returnere et projekt med subprojekter og tasks til projekt.html
    */
    public Project findFullProject(int id){

        // Vi henter ét bestemt projekt ud fra projektets id
        Project project = projectRepository.findById(id);

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

        // Først henter vi projekterne som tilhører session user id
        List<Project> projects = projectRepository.findByUserId(userId);

        //Vi declare count variabel
        int count = 0;

        // Vi looper igennem hver projekt for at tjekke hvad for en status de har
        for (Project project : projects){
            // if statement tjekker om projekt status matcher
            if (project.getStatus() == status){
                // For hver gang den matcher så plusser vi én gang og lægger det til vores count variabel
                count++;
            }
        }
        // Til sidst returnerer vi en sum på hvor mange har den specifikke status
        return count;
    }

}
