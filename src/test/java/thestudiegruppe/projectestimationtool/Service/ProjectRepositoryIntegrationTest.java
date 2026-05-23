package thestudiegruppe.projectestimationtool.Service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import thestudiegruppe.projectestimationtool.Model.Project;
import thestudiegruppe.projectestimationtool.Model.Status;
import thestudiegruppe.projectestimationtool.Repository.ProjectRepository;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = BEFORE_TEST_METHOD)
public class ProjectRepositoryIntegrationTest {

   @Autowired
    private ProjectRepository projectRepository;


   @Test
    void findAll_ShouldReturnProjectsFromDatabase(){
       //Arrange: Vi henter som regel bare den data der allerede ligger inde i h2init.sql.


       //Act: Henter alle projekter fra H2-databasen.
       List<Project> projects = projectRepository.findAll();

       //Assert: Vi tjekker, at h2init.sql har oprettet 2 projekter.
      assertThat(projects)
              .hasSize(2);

       //Assert: Tjekker at testprojekterne fra h2init.sql findes.
       assertThat(projects)
               .extracting(Project::getName)
               .contains("Test Project 1", "Test Project 2");

   }

   @Test
    void addProject_ShouldSaveNewProjectInDatabase(){
       //Arrange: Lav testData.
       Project project = new Project();
       project.setName("Test Project 3");
       project.setDescription("Test description 3");
       project.setDate(LocalDate.of(2026,5, 24));
       project.setDeadline(LocalDate.of(2026,5, 27));
       project.setStatus(Status.DONE);
       project.setBudget(100);
       int userId = 1;

       //Act: Gemmer et nyt projekt i H2-databasen.
       projectRepository.add(project, userId);


      //Assert: Henter alle projekter igen efter add.
       List<Project> projects = projectRepository.findAll();

       //Assert: Tjekker at det nye projekt findes i databasen.
       assertThat(projects)
               .extracting(Project::getName)
               .contains("Test Project 3");
   }

   @Test
    void deleteProject_ShouldDeleteProjectFromDatabase(){
       //Arrange: Vi vælger et projectId, som allerede findes i h2init.sql.
       int projectId = 1;

       //Act: Sletter projektet med det valgte id.
       projectRepository.delete(projectId);

      // Assert: Henter alle projekter efter delete.
       List<Project> projects = projectRepository.findAll();

      //Assert: Tjekker at projektets id ikke længere findes.
       assertThat(projects)
               .extracting(Project::getId)
               .doesNotContain(projectId);
   }

   @Test
    void findById_ShouldReturnProject_WhenProjectExists(){
       //Arrange: Vi vælger et projectId, som allerede findes i h2init.sql.
       int projectId = 1;

       //Act: Finder et projekt ud fra id.
       Project foundProject = projectRepository.findById(projectId);

      // Assert: Tjekker at der faktisk blev fundet et projekt.
       assertThat(foundProject).isNotNull();

      // Assert: Tjekker at projektets id matcher det id, vi søgte efter.
       assertThat(foundProject.getId()).isEqualTo(projectId);
   }

   @Test
    void updateProject_ShouldUpdateProjectInDatabase(){
       //Arrange: Vi vælger et projectId, som allerede findes i h2init.sql.
       int projectId = 1;

       //Arrange:
       Project project = new Project();
       project.setId(projectId);
       project.setName("Test Project Update 1");
       project.setDescription("Test description Update 1");
       project.setDate(LocalDate.of(2027,5, 22));
       project.setDeadline(LocalDate.of(2027,5, 23));
       project.setStatus(Status.TODO);
       project.setBudget(100);
       project.setUserId(1);

       //Act: Opdaterer projektet i H2-databasen.
       projectRepository.update(project);

       //Assert: Henter projektet igen og tjekker de nye værdier.
       Project updatedProject = projectRepository.findById(projectId);

      // Assert: Tjekker at projektet stadig findes efter update.
       assertThat(updatedProject).isNotNull();

      // Assert: Tjekker at projektet har de nye værdier fra update.
       assertThat(updatedProject.getId()).isEqualTo(projectId);
       assertThat(updatedProject.getName()).isEqualTo("Test Project Update 1");
       assertThat(updatedProject.getDescription()).isEqualTo("Test description Update 1");
       assertThat(updatedProject.getDate()).isEqualTo(LocalDate.of(2027,5, 22));
       assertThat(updatedProject.getStatus()).isEqualTo(Status.TODO);
       assertThat(updatedProject.getBudget()).isEqualTo(100);
   }

   @Test
    void findProjectByUserId_ShouldReturnProjectsForUser(){
       //Arrange: Vi vælger et userId, som allerede har projekter i h2init.sql.
       int userId = 1;

       //Act: Vi henter alle projekter, der tilhører userId 1.
       List<Project> foundProjects = projectRepository.findByUserId(userId);

       //Assert: Vi tjekker, at listen ikke er null.
       assertThat(foundProjects).isNotNull();

       //Assert: Vi tjekker, at der er 2 projekter for userId 1.
      assertThat(foundProjects).hasSize(2);

      //Assert: Vi tjekker, at alle projekterne faktisk tilhører userId 1.
      assertThat(foundProjects)
              .extracting(Project::getUserId)
              .containsOnly(userId);


   }


}
