package thestudiegruppe.projectestimationtool.Service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import thestudiegruppe.projectestimationtool.Model.Project;
import thestudiegruppe.projectestimationtool.Model.Status;
import thestudiegruppe.projectestimationtool.Model.User;
import thestudiegruppe.projectestimationtool.Repository.ProjectRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {


    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;


    @Test
    void createProjectShouldCreateProjectSuccessfully() {

      //Arrange: Lave testdata

        Project project = new Project();

       Mockito.when(projectRepository.add(project)).thenReturn(1);

      //Act: Kalde metoden som vi vil teste, fra service klassen

        projectService.createProject(project);
      //Assert: Kalder funktionen i service klassen, repository korrekt

        Mockito.verify(projectRepository).add(project);

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
    void findProjectById_shouldThrowExceptionWhenProjectNotFound() {

        //Arrange: Lave testdata
        int projectId = 1;

        Mockito.when(projectRepository.findById(projectId)).thenReturn(null);


        //Act: Kalde metoden som testes fra serviceklassen

        Assertions.assertThrows(RuntimeException.class, () -> projectService.findProjectById(projectId));


        //Assert: Kalder funktionen i service klassen, repository korrekt

        Mockito.verify(projectRepository).findById(projectId);
    }


    @Test
    void findProjectsByUserId_shouldThrowExceptionWhenUserNotFound() {

        //Arrange: Lave testdata

        int userId = 1;

        Mockito.when(projectRepository.findByUserId(userId)).thenReturn(null);

        //Act: Kalde metoden som testes fra serviceklassen

        Assertions.assertThrows(RuntimeException.class, () -> projectService.findProjectByUserId(userId));

        //Assert: Kalder funktionen i service klassen, repository korrekt

        Mockito.verify(projectRepository).findByUserId(userId);

    }




}
