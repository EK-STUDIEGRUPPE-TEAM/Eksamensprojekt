package thestudiegruppe.projectestimationtool.Repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import thestudiegruppe.projectestimationtool.Model.SubProject;

import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest

@ActiveProfiles ("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = BEFORE_TEST_METHOD)
public class SubProjectRepositoryIntegrationsTest {

    @Autowired
    private SubProjectRepository subProjectRepository;

    @Test
    void getAllSubProjects_ShouldReturnSubProjectsFromDatabase() {

        // Act
        List <SubProject> subProjects = subProjectRepository.getAllSubProjects();

        // Assert
        assertThat(subProjects).hasSize(2);

        // Assert
        assertThat(subProjects)
                .extracting(SubProject::getName)
                .contains("Test SubProject 1", "Test SubProject 2");
    }

    @Test
    void addSubProject_ShouldAddASubprojectToDatabase(){

        // Arrange
        SubProject subProject = new SubProject();
        subProject.setName("Test 3");
        subProject.setProjectId(1);

        // Act
        subProjectRepository.addSubProject(subProject);
        List<SubProject> subProjects = subProjectRepository.getAllSubProjects();

        // Assert
        assertThat(subProjects)
                .extracting(SubProject::getName)
                .contains("Test 3");

        //Assert
        assertThat(subProjects).hasSize(3);
    }

    @Test
    void getSubProjectsByProjectId_shouldReturnSubProjectFromProjectId(){
        // Arrange
        int projectId = 1;

        // Act
        List<SubProject> subProjects = subProjectRepository.getSubProjectsByProjectId(projectId);


        // Assert
        assertThat(subProjects).hasSize(2);

        //Assert
        assertThat(subProjects)
                .extracting(SubProject::getName)
                .contains("Test SubProject 1","Test SubProject 2");
    }

    @Test
    void updateSubProject_ShouldUpdateSubProjectInDatabase() {

        //Arrange
        SubProject subProject = subProjectRepository.findById(1);
        subProject.setName("Opdateret Subproject");

        //Act
        subProjectRepository.updateSubProject(subProject);

        //Assert
        SubProject updatedSubProject = subProjectRepository.findById(1);

        //Assert
        assertThat(updatedSubProject.getName()).isEqualTo("Opdateret Subproject");
    }

    @Test
    void deleteSubProject_shouldDeleteSubProject(){
        //Arrange

        //Act
        subProjectRepository.deleteSubProject(1);
        List<SubProject> subProjects = subProjectRepository.getAllSubProjects();

        //Assert
        assertThat(subProjects).hasSize(1);

        //Assert
        assertThat(subProjects)
                .extracting(SubProject::getName)
                .doesNotContain("Test SubProject 1");
    }
}
