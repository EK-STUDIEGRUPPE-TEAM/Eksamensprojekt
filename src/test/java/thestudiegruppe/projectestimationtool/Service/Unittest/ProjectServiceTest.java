package thestudiegruppe.projectestimationtool.Service.Unittest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import thestudiegruppe.projectestimationtool.Exception.NegativeValueException;
import thestudiegruppe.projectestimationtool.Exception.NotFoundException;
import thestudiegruppe.projectestimationtool.Model.*;
import thestudiegruppe.projectestimationtool.Repository.ProjectRepository;
import thestudiegruppe.projectestimationtool.Service.ProjectService;
import thestudiegruppe.projectestimationtool.Service.SubProjectService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


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

        //Arrange
        Project project = new Project();
        int userId = 1;

        Mockito.when(projectRepository.add(project, userId)).thenReturn(1);

        //Act
        projectService.createProject(project, userId);

        //Assert
        Mockito.verify(projectRepository).add(project, userId);

    }

    @Test
    void createProject_shouldReturnExceptionWhenVBudgetIsNegative(){

        //Arrange
        Project project = new Project();
        project.setBudget(-1);
        int userId = 1;

        //Act
        assertThrows(NegativeValueException.class, () -> {
            projectService.createProject(project, userId);
        });

        //Assert
        verify(projectRepository, never()).add(project, userId);
    }


    @Test
    void getAllProjectsShouldReturnAllProjectsSuccessfully() {

        //Arrange
        List<Project> projects = new ArrayList<>();

        projects.add(new Project());
        projects.add(new Project());

        Mockito.when(projectRepository.findAll()).thenReturn(projects);

        //Act
        projectService.findAllProjects();

        //Assert
        Mockito.verify(projectRepository).findAll();


    }

    @Test
    void getAllProjectsByUserIdShouldReturnProjectSuccessfully() {

        //Arrange
        List<Project> projects = new ArrayList<>();
        projects.add(new Project());
        projects.add(new Project());

        User user = new User();
        user.setId(1);

        Mockito.when(projectRepository.findByUserId(user.getId())).thenReturn(projects);


        //Act
        projectService.findProjectByUserId(user.getId());

        //Assert
        Mockito.verify(projectRepository).findByUserId(user.getId());

    }

    @Test
    void updateProjectShouldUpdateProjectSuccessfully() {

        //Arrange
        Project project = new Project();

        //Act
        projectService.updateProject(project);

        //Assert
        Mockito.verify(projectRepository).update(project);
    }

    @Test
    void updateProject_shouldReturnExceptionWhenVBudgetIsNegative(){

        //Arrange
        Project project = new Project();
        project.setBudget(-1);

        //Act
        assertThrows(NegativeValueException.class, () -> {
            projectService.updateProject(project);
        });

        //Assert
        verify(projectRepository, never()).update(project);
    }

    @Test
    void deleteProjectShouldDeleteProjectSuccessfully() {

        //Arrange
        Project project = new Project();
        project.setId(1);

        //Act
        projectService.deleteProject(project.getId());

        //Assert
        Mockito.verify(projectRepository).delete(project.getId());
    }

    @Test
    void getProjectByIdShouldReturnProjectSuccessfully() {

        //Arrange
        Project project = new Project();
        project.setId(1);

        Mockito.when(projectRepository.findById(project.getId())).thenReturn(project);

        //Act
        projectService.findProjectById(project.getId());

        //Assert
        Mockito.verify(projectRepository).findById((project.getId()));

    }

    @Test
    void findProjectById_shouldThrowNotFoundExceptionWhenProjectNotFound() {

        //Arrange
        int projectId = 1;

        when(projectRepository.findById(projectId)).thenThrow(new EmptyResultDataAccessException(1));


        //Act
        assertThrows(NotFoundException.class, () -> projectService.findProjectById(projectId));


        //Assert
        verify(projectRepository).findById(projectId);
    }



    @Test
    void findFullProject_shouldReturnProjectWithSubProjects() {

        //Arrange
        Project project = new Project();
        project.setId(1);

        SubProject subProject = new SubProject();
        subProject.setProjectId(1);

        List<SubProject> subProjects = List.of(subProject);


        when(projectRepository.findById(1)).thenReturn(project);
        when(subProjectService.getFullSubProjects(1)).thenReturn(subProjects);

        //Act
        Project result = projectService.findFullProject(1);

        //Assert
        assertEquals(subProjects, result.getSubProjects());
        verify(subProjectService, Mockito.times(1)).getFullSubProjects(1);
    }


    @Test
    void projectsWithStatusCount_shouldReturnCorrectCount_whenProjectsMatchStatus() {

        //Arrange
        int userId = 1;
        Project project1 = new Project();
        project1.setStatus(Status.TODO);

        Project project2 = new Project();
        project2.setStatus(Status.TODO);

        Project project3 = new Project();
        project3.setStatus(Status.DONE);

        List<Project> projects = List.of(project1, project2, project3);

        when(projectRepository.findByUserId(userId)).thenReturn(projects);

        //Act
        int result = projectService.projectsWithStatusCount(userId, Status.TODO);

        //Assert
        assertEquals(2, result);
        verify(projectRepository).findByUserId(userId);
    }

    @Test
    void findProjectsByUserId_shouldReturnEmptyList_whenUserHasNoProject() {

        //Arrange
        int userId = 1;
        List<Project> emptyProjects = new ArrayList<>();

        when(projectRepository.findByUserId(userId)).thenReturn(emptyProjects);

        //Act
        List<Project> result = projectService.findProjectByUserId(userId);

        // Assert
        Assertions.assertTrue(result.isEmpty());
        Mockito.verify(projectRepository).findByUserId(userId);

    }

    @Test
    void getTotalEstimatedHoursOfWholeProject_shouldReturnTotalEstimatedHours() {

        //Arrange
        int projectId = 1;

        Project project = new Project();

        Task task1 = new Task();
        task1.setEstimatedHours(3);

        Task task2 = new Task();
        task2.setEstimatedHours(5);

        SubProject subProject = new SubProject();
        subProject.setTasks(List.of(task1, task2));

        when(projectRepository.findById(projectId)).thenReturn(project);
        when(subProjectService.getFullSubProjects(projectId)).thenReturn(List.of(subProject));

        //Act
        double result = projectService.getTotalEstimatedHoursOfWholeProject(projectId);

        //Assert
        assertEquals(8.0, result, 0.001);
    }

    @Test
    void getTotalPriceOfWholeProject_shouldReturnTotalPrice() {

        //Arrange
        int projectId = 1;

        Project project = new Project();

        Task task1 = new Task();
        task1.setTotalPrice(600);

        Task task2 = new Task();
        task2.setTotalPrice(1000);

        SubProject subProject = new SubProject();
        subProject.setTasks(List.of(task1, task2));

        when(projectRepository.findById(projectId)).thenReturn(project);
        when(subProjectService.getFullSubProjects(projectId)).thenReturn(List.of(subProject));

        //Act
        double result = projectService.getTotalPriceOfWholeProject(projectId);

        //Assert
        assertEquals(1600.0, result, 0.001);
    }

    @Test
    void getProjectDifference(){

        //Arrange
        int projectId = 1;

        Project project = new Project();
        project.setBudget(3000);

        Task task1 = new Task();
        task1.setTotalPrice(500);

        Task task2 = new Task();
        task2.setTotalPrice(1000);

        SubProject subProject = new SubProject();
        subProject.setTasks(List.of(task1, task2));

        when(projectRepository.findById(projectId)).thenReturn(project);
        when(subProjectService.getFullSubProjects(projectId)).thenReturn(List.of(subProject));

        //Act
        double result = projectService.getProjectDifference(projectId);

        //Assert
        assertEquals(1500, result);
        assertTrue(result > 0);
    }
}
