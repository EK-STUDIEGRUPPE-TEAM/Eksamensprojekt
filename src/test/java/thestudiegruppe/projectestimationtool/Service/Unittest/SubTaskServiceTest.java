package thestudiegruppe.projectestimationtool.Service.Unittest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import thestudiegruppe.projectestimationtool.Exception.NegativeValueException;
import thestudiegruppe.projectestimationtool.Model.SubTask;
import thestudiegruppe.projectestimationtool.Repository.SubTaskRepository;
import thestudiegruppe.projectestimationtool.Service.SubTaskService;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static thestudiegruppe.projectestimationtool.Model.Status.*;

@ExtendWith(MockitoExtension.class)
class SubTaskServiceTest {

    @Mock
    private SubTaskRepository repository;

    @InjectMocks
    private SubTaskService service;

    @Test
    void createSubTask_ShouldCallRepository() {

        //Arrange
        SubTask subTask = new SubTask();
        subTask.setEstimatedHours(10);

       //Act
        service.createSubTask(subTask);

        //Assert
        verify(repository, times(1)).addSubTask(subTask);
    }

    @Test
    void getSubtaskById_ShouldReturnSubTaskSuccessfully() {

        //Arrange
        SubTask subTask = new SubTask();
        subTask.setId(1);

        when(repository.findSubTaskById(subTask.getId())).thenReturn(subTask);

        //Act
        service.getSubTaskById(subTask.getId());

        //Assert
        verify(repository).findSubTaskById((subTask.getId()));
    }

    @Test
    void getSubTaskByTaskId_ShouldReturnSubTaskId() {

        //Arrange
        SubTask subTask = new SubTask();

        List<SubTask> subTasks = List.of(subTask, subTask);

        //Act
        when(repository.findSubTaskByTaskId(1)).thenReturn(subTasks);

        List<SubTask> result = service.getSubTasksByTaskId(1);

        //Assert
        assertEquals(subTasks, result);
    }

    @Test
    void deleteSubTask_ShouldCallRepository() {

        //Arrange
        int id = 1;

        //Act
        service.deleteSubTask(id);

        //Assert
        verify(repository, times(1)).deleteSubTask(id);
    }

    @Test
    void updateSubTask_ShouldCallRepository() {

        //Arrange
        SubTask subTask = new SubTask();
        subTask.setEstimatedHours(10);

        //Act
        service.updateSubTask(subTask);

        //Assert
        verify(repository, times(1)).updateSubTask(subTask);
    }

    @Test
    void deleteSubTaskByTaskId_ShouldCallRepository() {

        //Arrange
        int taskId = 1;

        //Act
        service.deleteSubTaskByTaskId(taskId);

        //Assert
        verify(repository, times(1)).deleteSubTaskByTaskId(taskId);
    }

    @Test
    void calculateEstimatedHours_ShouldReturnTotal() {

        //Arrange
        List<SubTask> subTasks = List.of(
                new SubTask(1, "Test subtask", "test", 120, DONE, 1),
                new SubTask(2, "Test subtask2", "test", 380, DONE, 1)
        );

        //Act
        when(repository.findSubTaskByTaskId(1)).thenReturn(subTasks);
        int result = service.calculateEstimatedHours(1);

       //Assert
        assertEquals(500, result);
    }

    @Test
    void createSubTask_ShouldThrowNegativeValueException_WhenHoursAreNegative() {

        //Arrange
        SubTask subTask = new SubTask(1, "Test", "Test desc", -10, DONE, 1);

       //Act + assert
        assertThrows(NegativeValueException.class, () -> service.createSubTask(subTask));
    }

    @Test
    void updateSubTask_ShouldThrowNegativeValueException_WhenHoursAreNegative() {

        //Arrange
        SubTask subTask = new SubTask(1, "Test", "Test desc", -10, DONE, 1);

        //Act + Assert
        assertThrows(NegativeValueException.class, () -> service.updateSubTask(subTask));
    }


}