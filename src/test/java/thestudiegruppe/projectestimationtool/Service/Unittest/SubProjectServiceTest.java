package thestudiegruppe.projectestimationtool.Service.Unittest;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import thestudiegruppe.projectestimationtool.Model.SubProject;
import thestudiegruppe.projectestimationtool.Model.Task;
import thestudiegruppe.projectestimationtool.Repository.SubProjectRepository;
import thestudiegruppe.projectestimationtool.Service.SubProjectService;
import thestudiegruppe.projectestimationtool.Service.TaskService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SubProjectServiceTest {

    @Mock
    private SubProjectRepository subProjectRepository;

    @Mock
    private TaskService taskService;

    @InjectMocks
    private SubProjectService subProjectService;

    @Test
    void getAllSubProjectsShouldReturnList() {

       //Arrange
        int projectId = 1;
        SubProject subProject = new SubProject(1, "test SubProject", projectId);

        when(subProjectRepository.getAllSubProjects()).thenReturn(List.of(subProject));

        //Act
        List<SubProject> result = subProjectService.getAllSubProjects();

        //Assert
        assertEquals(1, result.size());
        assertEquals("test SubProject", result.get(0).getName());
    }

    @Test
    void deleteSubProjectShouldCallRepository() {

        //Arrange
        int subProjectId = 1;

        //Act
        subProjectService.deleteSubProject(subProjectId);

        //Assert
        verify(subProjectRepository).deleteSubProject(subProjectId);
    }


    @Test
    void createSubProjectShouldCallRepository() {

        //Arrange
        SubProject subProject = new SubProject();

        //Act
        subProjectService.createSubProject(subProject);

        //Assert
        verify(subProjectRepository, times(1)).addSubProject(subProject);
    }

    @Test
    void updateSubProjectShouldCallRepository() {

        //Arrange
        int projectId = 1;
        SubProject subProject = new SubProject(1, "test", projectId);

        //Act
        subProjectService.updateSubProject(subProject);

        //Assert
        verify(subProjectRepository).updateSubProject(subProject);
    }

    @Test
    void getSubProjectsByProjectId_shouldReturnSubProjects_whenProjectIdIsValid(){

        //Arrange
        int projectId = 1;

        SubProject subProject1 = new SubProject();
        SubProject subProject2 = new SubProject();

        List<SubProject> subProjects = List.of(subProject1, subProject2);

        when(subProjectRepository.getSubProjectsByProjectId(projectId)).thenReturn(subProjects);

        //Act
        List<SubProject> result = subProjectService.getSubProjectsByProjectId(projectId);

        //Assert
        assertEquals(subProjects, result);

        //Assert
        verify(subProjectRepository, times(1)).getSubProjectsByProjectId(projectId);
    }

    // Tester at getFullSubProjects returnerer subprojekter med de korrekte tasks sat på.
    @Test
    void getFullSubProjects_shouldReturnSubProjectsWithTasks() {

        //Arrange
        SubProject subProject = new SubProject();
        subProject.setId(1);

        Task task = new Task();
        task.setSubProjectId(1);

        List<SubProject> subProjects = List.of(subProject);
        List<Task> tasks = List.of(task);

        when(subProjectRepository.getSubProjectsByProjectId(1)).thenReturn(subProjects);
        when(taskService.getFullTasks(1)).thenReturn(tasks);

        //Act
        List<SubProject> result = subProjectService.getFullSubProjects(1);

        //Assert
        assertEquals(1, result.size());
        assertEquals(tasks, result.get(0).getTasks());
        verify(taskService, times(1)).getFullTasks(1);
    }

    @Test
    void getSubProjectByIdShouldReturnSubProjectSuccessfully() {

        //Arrange
        SubProject subProject = new SubProject();
        subProject.setId(1);

        when(subProjectRepository.findById(subProject.getId())).thenReturn(subProject);

        //Act
        subProjectService.findSubProjectById(subProject.getId());

        //Assert
        verify(subProjectRepository).findById((subProject.getId()));
    }
}

