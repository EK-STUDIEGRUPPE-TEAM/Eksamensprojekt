package thestudiegruppe.projectestimationtool.Repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import thestudiegruppe.projectestimationtool.Model.Project;
import thestudiegruppe.projectestimationtool.Model.Status;

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

       //Act
       List<Project> projects = projectRepository.findAll();

       //Assert
      assertThat(projects)
              .hasSize(2);

       //Assert
       assertThat(projects)
               .extracting(Project::getName)
               .contains("Test Project 1", "Test Project 2");

   }

   @Test
    void addProject_ShouldSaveNewProjectInDatabase(){

      //Arrange
       Project project = new Project();
       project.setName("Test Project 3");
       project.setDescription("Test description 3");
       project.setDate(LocalDate.of(2026,5, 24));
       project.setDeadline(LocalDate.of(2026,5, 27));
       project.setStatus(Status.DONE);
       project.setBudget(100);

       int userId = 1;

       //Act
       projectRepository.add(project, userId);


      //Assert
       List<Project> projects = projectRepository.findAll();

       //Assert
       assertThat(projects)
               .extracting(Project::getName)
               .contains("Test Project 3");
   }

   @Test
    void deleteProject_ShouldDeleteProjectFromDatabase(){

      //Arrange
       int projectId = 1;

       //Act
       projectRepository.delete(projectId);

      // Assert
       List<Project> projects = projectRepository.findAll();

      //Assert
       assertThat(projects)
               .extracting(Project::getId)
               .doesNotContain(projectId);
   }

   @Test
    void findById_ShouldReturnProject_WhenProjectExists(){
       //Arrange
       int projectId = 1;

       //Act
       Project foundProject = projectRepository.findById(projectId);

      // Assert
       assertThat(foundProject).isNotNull();

      // Assert
       assertThat(foundProject.getId()).isEqualTo(projectId);
   }

   @Test
    void updateProject_ShouldUpdateProjectInDatabase(){
       //Arrange
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

       //Act
       projectRepository.update(project);

       //Assert
       Project updatedProject = projectRepository.findById(projectId);

      // Assert
       assertThat(updatedProject).isNotNull();

      // Assert
       assertThat(updatedProject.getId()).isEqualTo(projectId);
       assertThat(updatedProject.getName()).isEqualTo("Test Project Update 1");
       assertThat(updatedProject.getDescription()).isEqualTo("Test description Update 1");
       assertThat(updatedProject.getDate()).isEqualTo(LocalDate.of(2027,5, 22));
       assertThat(updatedProject.getDeadline()).isEqualTo(LocalDate.of(2027,5, 23));
       assertThat(updatedProject.getStatus()).isEqualTo(Status.TODO);
       assertThat(updatedProject.getBudget()).isEqualTo(100);
   }

   @Test
    void findProjectByUserId_ShouldReturnProjectsForUser(){
       //Arrange
       int userId = 1;

       //Act
       List<Project> foundProjects = projectRepository.findByUserId(userId);

       //Assert
       assertThat(foundProjects).isNotNull();

       //Assert
      assertThat(foundProjects).hasSize(2);

      //Assert
      assertThat(foundProjects)
              .extracting(Project::getUserId)
              .containsOnly(userId);
   }
}
