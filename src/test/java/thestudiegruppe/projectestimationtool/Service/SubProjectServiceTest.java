package thestudiegruppe.projectestimationtool.Service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import thestudiegruppe.projectestimationtool.Model.SubProject;
import thestudiegruppe.projectestimationtool.Repository.SubProjectRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SubProjectServiceTest {

    //Laver fake version af repository
    @Mock
    private SubProjectRepository subProjectRepository;

    //Laver service-klasse og sætter vores fake version repository ind i den
    @InjectMocks
    private SubProjectService subProjectService;

    //test for at tjekke om der returneres den liste som repository giver tilbage
    @Test
    void getAllSubProjectsShouldReturnList() {

        //Her adder vi et project objekt og sætter dataen ind i et subProject
        int projectId = 1;
        SubProject subProject = new SubProject(1, "test SubProject", "test", projectId);

        // Viser at når repository bliver kaldt, så returner det test dataen
        when(subProjectRepository.getAllSubProjects()).thenReturn(List.of(subProject));

        // tester om serivce-metoden virker
        List<SubProject> result = subProjectService.getAllSubProjects();

        //Tjekker om vores resultat er det vi forventer
        //om vi kan se SubProject med navnet "test Subprject" i listen
        assertEquals(1, result.size());
        assertEquals("test SubProject", result.get(0).getName());
    }

    //Tjekker om service kalder korrekt til repository
    @Test
    void deleteSubProjectShouldCallRepository() {
        //Arrange: laver testData.
        int subProjectId = 1;

        // kalder service
        subProjectService.deleteSubProject(subProjectId);

        // tjekker om repository er blevet kaldt med værdien 1
        verify(subProjectRepository).deleteSubProject(subProjectId);
    }

    //test om createSubProject() bliver korrekt kaldt fra SubProjectService til SubProjectRepository
    @Test
    void createSubProjectShouldCallRepository() {
        //Arrange: Laver testdata
        int projectId = 1;
        SubProject subProject = new SubProject(1, "test", "SubProject calls Repo", projectId);

        //Her sætter vi vores lavet subprojekt ind i vores service
        subProjectService.createSubProject(projectId, subProject);

        //Tjekker om vores service kalder til repository automatisk
        verify(subProjectRepository).addSubProject(projectId, subProject);
    }

    @Test
    void updateSubProjectShouldCallRepository() {
        // Arrange: laver testdata
        int projectId = 1;
        SubProject subProject = new SubProject(1, "test", "test for update", projectId);

        // Her kaldes update i service
        subProjectService.updateSubProject(subProject);

        // Tjekker om repository bliver kaldt korrekt
        verify(subProjectRepository).updateSubProject(subProject);

    }

    @Test
    void getSubProjectsByProjectId_shouldReturnSubProjects_whenProjectIdIsValid(){
        //Arrange: Vi laver testData.
        int projectId = 1;

        SubProject subProject1 = new SubProject();
        SubProject subProject2 = new SubProject();

        /* Vi samler vores subprojects i en liste,
           som repository skal returnere. */
        List<SubProject> subProjects = List.of(subProject1, subProject2);

        /* Vi fortæller mock-repository, at den skal returnere listen,
           når getSubProjectsByProjectId(projectId) bliver kaldt. */
        when(subProjectRepository.getSubProjectsByProjectId(projectId)).thenReturn(subProjects);

        // Act: Vi kalder service-metoden, som vi vil teste.
        List<SubProject> result = subProjectService.getSubProjectsByProjectId(projectId);

        /* Assert: Vi tjekker, at resultatet fra service
           er den samme liste, som repository returnerede. */
        assertEquals(subProjects, result);

        // Vi tjekker også, at repository-metoden blev kaldt præcis 1 gang.
        verify(subProjectRepository, times(1)).getSubProjectsByProjectId(projectId);


    }
}

