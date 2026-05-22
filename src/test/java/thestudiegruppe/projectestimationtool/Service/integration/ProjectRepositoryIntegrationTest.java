package thestudiegruppe.projectestimationtool.Service.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import thestudiegruppe.projectestimationtool.Model.Project;
import thestudiegruppe.projectestimationtool.Model.Status;
import thestudiegruppe.projectestimationtool.Model.User;
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
    void findAll(){
       //Arrange: Vi henter som regelt bare den data der allerede ligger inde i h2init.sql.


       //Act:
       List<Project> projects = projectRepository.findAll();

       //Assert:
       assertThat(projects)
               .extracting(Project::getName)
               .contains("Test Project 1", "Test Project 2");

   }

   @Test
    void addProject(){
       //Arrange: Lav testData.
       Project project = new Project();
       project.setName("Test Project 3");
       project.setDescription("Test description 3");
       project.setDate(LocalDate.of(2026,5, 24));
       project.setStatus(Status.DONE);
       int userId = 1;

       //Act: Kalder metoden der gemmer et nyt project.
       projectRepository.add(project, userId);

       //Assert:
       List<Project> projects = projectRepository.findAll();

       assertThat(projects)
               .extracting(Project::getName)
               .contains("Test Project 3");
   }

   @Test
    void deleteProject(){
       //Arrange: Vi vælger et projectId, som allerede findes i h2init.sql.
       int projectId = 1;

       //Act:
       projectRepository.delete(projectId);

       //Assert:
       List<Project> projects = projectRepository.findAll();

       //Assert:
       assertThat(projects)
               .extracting(Project::getId)
               .doesNotContain(projectId);
   }

   @Test
    void findById(){
       //Arrange: Vi vælger et projectId, som allerede findes i h2init.sql.
       int projectId = 1;

       //Act:
       Project foundProject = projectRepository.findById(projectId);

       //Assert:
       assertThat(foundProject).isNotNull();

       //Assert:
       assertThat(foundProject.getId()).isEqualTo(projectId);
   }

//   @Test
//    void updateProject(){
//       //Arrange: Vi vælger et projectId, som allerede findes i h2init.sql.
//       int projectId = 1;
//
//       //Arrange:
//       Project project = new Project();
//       project.setName("Test Project Update 1");
//       project.setDescription("Test description Update 1");
//       project.setDate(LocalDate.of(2027,5, 22));
//       project.setStatus(Status.TODO);
//
//       //Act:
//       projectRepository.update(project);
//
//       //Assert:
//       Project updatedProject = projectRepository.findById(projectId);
//
//       //Assert:
//       assertThat(updatedProject).isNotNull();
//
//       //Assert:
//       assertThat(updatedProject.getId()).isEqualTo(projectId);
//       assertThat(updatedProject.getName()).isEqualTo("Test Project Update 1");
//       assertThat(updatedProject.getDescription()).isEqualTo("Test description Update 1");
//       assertThat(updatedProject.getDate()).isEqualTo(LocalDate.of(2027,5, 22));
//       assertThat(updatedProject.getStatus()).isEqualTo(Status.TODO);
//   }

//   @Test
//    void findProjectByUserId(){
//       //Arrange:
//       int userId = 1;
//
//       //Act:
//       List<Project> foundProject = projectRepository.findByUserId(userId);
//
//       //Arrange:
//       assertThat(foundProject).isNotNull();
//
//       //Arrange:
//       assertThat(foundProject).isEqualTo()
//
//
//   }


}
