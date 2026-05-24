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

// @ActiveProfiles("test") gør, at testen bruger application-test.properties.
// Derfor bruger testen H2-databasen i stedet for den normale database.
@ActiveProfiles ("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = BEFORE_TEST_METHOD)
public class SubProjectRepositoryIntegrationsTest {

    //Indsætter rigtig repository
    @Autowired
    private SubProjectRepository subProjectRepository;

    @Test
    void getAllSubProjects_ShouldReturnSubProjectsFromDatabase() {
        //Arrange: testdata er lavet i H2init.sql


        // Act: Henter alle SubProject fra database
        List <SubProject> subProjects = subProjectRepository.getAllSubProjects();

        // Assert: Vi tjekker om h2init.sql har oprettet de to subprojects der er indsat
        assertThat(subProjects).hasSize(2);

        // Assert: Her henter vi testdataen og bruger .contains til at sikre os at det er den korrekte data
        assertThat(subProjects)
                .extracting(SubProject::getName)
                .contains("Test SubProject 1", "Test SubProject 2");
    }

    @Test
    void addSubProject_ShouldAddASubprojectToDatabase(){

        // Arrange: Vi laver et nyt subproject og indsætter data
        SubProject subProject = new SubProject();
        subProject.setName("Test 3");
        subProject.setProjectId(1);

        // Act: Vi adder vores subProject
        subProjectRepository.addSubProject(subProject);
        List<SubProject> subProjects = subProjectRepository.getAllSubProjects();

        // Assert: Vi tjekker om subprojects indeholder vores data
        // Vi tjekker om navnet eksisterer i databasen
        assertThat(subProjects)
                .extracting(SubProject::getName)
                .contains("Test 3");

        // Vi tjekker om databasen nu indeholder 3 istedet for 2
        assertThat(subProjects).hasSize(3);
    }

    @Test
    void getSubProjectsByProjectId_shouldReturnSubProjectFromProjectId(){
        // Arrange: Vi laver sætter projekt id til 1
        int projectId = 1;

        // Act: Vi henter subprojects fra fra projekt id
        List<SubProject> subProjects = subProjectRepository.getSubProjectsByProjectId(projectId);


        // Assert: Vi tjekker projektet indeholder vores subprojekter
        // Vi tjekker om projektet har 2 subprojekter
        assertThat(subProjects).hasSize(2);

        // Vi tjekker om projektet har navnene på subprojekterne
        assertThat(subProjects)
                .extracting(SubProject::getName)
                .contains("Test SubProject 1","Test SubProject 2");
    }

    @Test
    void updateSubProject_ShouldUpdateSubProjectInDatabase() {
        // Arrange: Vi henter et subproject fra databasen og ændrer navnet.
        SubProject subProject = subProjectRepository.findById(1);
        subProject.setName("Opdateret Subproject");

        // Act: Vi opdaterer subprojectet i H2-databasen.
        subProjectRepository.updateSubProject(subProject);

        // Assert: Vi henter subprojectet igen og tjekker den nye værdi.
        SubProject updatedSubProject = subProjectRepository.findById(1);

        assertThat(updatedSubProject.getName()).isEqualTo("Opdateret Subproject");
    }

    @Test
    void deleteSubProject_shouldDeleteSubProject(){
        //Arrange: Vi har allerede lavet dataen i H2init.sql

        // Act: Først sletter vi subproject fra id og så henter vi alle subprojects
        subProjectRepository.deleteSubProject(1);
        List<SubProject> subProjects = subProjectRepository.getAllSubProjects();

        // Assert: Vi tjekker om subprojectet er blevet slettet i databasen
        assertThat(subProjects).hasSize(1);

        assertThat(subProjects)
                .extracting(SubProject::getName)
                .doesNotContain("Test SubProject 1");
    }
}
