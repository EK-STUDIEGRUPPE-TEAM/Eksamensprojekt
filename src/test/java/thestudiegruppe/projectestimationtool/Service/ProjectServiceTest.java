package thestudiegruppe.projectestimationtool.Service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import thestudiegruppe.projectestimationtool.Exception.NotFoundException;
import thestudiegruppe.projectestimationtool.Model.Project;
import thestudiegruppe.projectestimationtool.Model.SubProject;
import thestudiegruppe.projectestimationtool.Model.Status;
import thestudiegruppe.projectestimationtool.Model.User;
import thestudiegruppe.projectestimationtool.Repository.ProjectRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {


    @Mock
    private ProjectRepository projectRepository;
    
    
    @Mock
    private SubProjectService subProjectService;

    @InjectMocks
    private ProjectService projectService;


    @Test
    void createProjectShouldCreateProjectSuccessfully() {

        //Arrange: Lave testdata
        Project project = new Project();
        int userId = 1;

        Mockito.when(projectRepository.add(project, userId)).thenReturn(1);

        //Act: Kalde metoden som vi vil teste, fra service klassen

        projectService.createProject(project, userId);
        //Assert: Kalder funktionen i service klassen, repository korrekt

        Mockito.verify(projectRepository).add(project, userId);

    }


    @Test
    void getAllProjectsShouldReturnAllProjectsSuccessfully() {

        //Arrange: Lave testdata
        List<Project> projects = new ArrayList<>();

        projects.add(new Project());
        projects.add(new Project());

        Mockito.when(projectRepository.findAll()).thenReturn(projects);

        //Act: Kalde metoden som testes fra serviceklassen

        projectService.findAllProjects();

        //Assert: Kalder funktionen i service klassen, repository korrekt

        Mockito.verify(projectRepository).findAll();


    }

    @Test
    void getAllProjectsByUserIdShouldReturnProjectSuccessfully() {

        //Arrange: Lave testdata
        List<Project> projects = new ArrayList<>();
        projects.add(new Project());
        projects.add(new Project());

        User user = new User();
        user.setId(1);

        Mockito.when(projectRepository.findByUserId(user.getId())).thenReturn(projects);


        //Act: Kalde metoden som testes fra serviceklassen

        projectService.findProjectByUserId(user.getId());


        //Assert: Kalder funktionen i service klassen, repository korrekt

        Mockito.verify(projectRepository).findByUserId(user.getId());

    }

    @Test
    void updateProjectShouldUpdateProjectSuccessfully() {

        //Arrange: Lave testdata
        Project project = new Project();

        //Act: Kalde metoden som testes fra serviceklassen
        projectService.updateProject(project);
        //Assert: Kalder funktionen i service klassen, repository korrekt

        Mockito.verify(projectRepository).update(project);
    }

    @Test
    void deleteProjectShouldDeleteProjectSuccessfully() {

        //Arrange: Lave testdata
        Project project = new Project();
        project.setId(1);

        //Act: Kalde metoden som testes fra serviceklassen
        projectService.deleteProject(project.getId());

        //Assert: Kalder funktionen i service klassen, repository korrekt
        Mockito.verify(projectRepository).delete(project.getId());

    }

    @Test
    void getProjectByIdShouldReturnProjectSuccessfully() {

        //Arrange: Lave testdata
        Project project = new Project();
        project.setId(1);

        Mockito.when(projectRepository.findById(project.getId())).thenReturn(project);

        //Act: Kalde metoden som testes fra serviceklassen

        projectService.findProjectById(project.getId());

        //Assert: Kalder funktionen i service klassen, repository korrekt

        Mockito.verify(projectRepository).findById((project.getId()));

    }

    @Test
    void findProjectById_shouldThrowNotFoundExceptionWhenProjectNotFound() {

        //Arrange: Lave testdata
        int projectId = 1;

        when(projectRepository.findById(projectId)).thenThrow(new EmptyResultDataAccessException(1));


        //Act: Kalde metoden som testes fra serviceklassen

        assertThrows(NotFoundException.class, () -> projectService.findProjectById(projectId));


        //Assert: Kalder funktionen i service klassen, repository korrekt

        verify(projectRepository).findById(projectId);
    }


    // Tester at findFullProject returnerer et projekt med de korrekte subprojekter sat på.
    @Test
    void findFullProject_shouldReturnProjectWithSubProjects() {
        // Arrange: Vi laver et projekt og et subprojekt, der hører til det.
        Project project = new Project();
        project.setId(1);

        SubProject subProject = new SubProject();
        subProject.setProjectId(1);

        List<SubProject> subProjects = List.of(subProject);

        // Repository returnerer projekte og subProjectService returnerer subprojekterne.
        when(projectRepository.findById(1)).thenReturn(project);
        when(subProjectService.getFullSubProjects(1)).thenReturn(subProjects);

        // Act: Vi kalder service-metoden.
        Project result = projectService.findFullProject(1);

        /* Assert: Vi tjekker at subprojektlisten er sat korrekt på projekt-objektet. */
        assertEquals(subProjects, result.getSubProjects());
        verify(subProjectService, Mockito.times(1)).getFullSubProjects(1);
    }

    // Tester at projectsWithStatusCount returnerer det korrekte antal projekter med en bestemt status.
    @Test
    void projectsWithStatusCount_shouldReturnCorrectCount_whenProjectsMatchStatus() {

        //Arrange: Vi laver 3 projekter hvor 2 har status TODO og 1 har status DONE.
        int userId = 1;
        Project project1 = new Project();
        project1.setStatus(Status.TODO);

        Project project2 = new Project();
        project2.setStatus(Status.TODO);

        Project project3 = new Project();
        project3.setStatus(Status.DONE);

        /* Vi samler dem i en liste, som repository skal returnere
        når findByUserId(userId) bliver kaldt. */
        List<Project> projects = List.of(project1, project2, project3);

        when(projectRepository.findByUserId(userId)).thenReturn(projects);

        //Act: Vi kalder service metoden med status TODO
        int result = projectService.projectsWithStatusCount(userId, Status.TODO);

        /* Assert: Vi tjekker, at resultatet er 2,
        fordi kun 2 ud af 3 projekter har status TODO */
        assertEquals(2, result);
        verify(projectRepository).findByUserId(userId);
    }

    @Test
    void findProjectsByUserId_shouldReturnEmptyList_whenUserHasNoProject() {

        //Arrange: Lave testdata
        int userId = 1;
        List<Project> emptyProjects = new ArrayList<>();

        when(projectRepository.findByUserId(userId)).thenReturn(emptyProjects);

        //Act: Kalde metoden som testes fra serviceklassen
        List<Project> result = projectService.findProjectByUserId(userId);

        // Assert: Tjekker at listen er tom, men uden at kaste exception
        Assertions.assertTrue(result.isEmpty());
        Mockito.verify(projectRepository).findByUserId(userId);

    }


}
