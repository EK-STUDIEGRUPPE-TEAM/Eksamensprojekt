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

        Project project = projectRepository.findById(id);

        /* Hvis repository ikke finder et projekt,
           kaster vi vores egen NotFoundException.

           Det gør vi, så systemet kan håndtere fejlen pænt,
           fx med en 404-side */
        if (project == null) {
            throw new NotFoundException("Projekt", id);
        }

        /* Hvis projektet findes, returnerer vi det */
        return project;
    }


}
