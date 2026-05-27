package thestudiegruppe.projectestimationtool.Service.Unittest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import thestudiegruppe.projectestimationtool.Model.Status;
import thestudiegruppe.projectestimationtool.Model.SubTask;
import thestudiegruppe.projectestimationtool.Model.Task;
import thestudiegruppe.projectestimationtool.Repository.TaskRepository;
import thestudiegruppe.projectestimationtool.Service.SubTaskService;
import thestudiegruppe.projectestimationtool.Service.TaskService;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private SubTaskService subTaskService;

    @InjectMocks
    private TaskService taskService;


    @Test
    void createTask_shouldCallRepositoryCreateTask_whenTaskIsNotNull() {

        //Arrange
        Task task = new Task();
        task.setHourlyRate(100);

        //Act
        taskService.createTask(task);

        //Assert
        verify(taskRepository, times(1)).addTask(task);
    }



    @Test
    void createTask_shouldThrowException_whenTaskIsNull() {

        //Arrange
        Task task = null;

        //Act + Assert
        assertThrows(IllegalArgumentException.class, () -> taskService.createTask(task));

        //Assert
        verify(taskRepository, never()).addTask(any(Task.class));
    }


    @Test
    void getAllTasksShouldReturnTasks() {

        //Arrange
        Task task1 = new Task(1, "Test Task 1", "Test for Task Service 1", LocalDate.of(2026,5,30), 250.0, Status.TODO, 1);
        Task task2 = new Task(2, "Test Task 2", "Test for Task Service 2", LocalDate.of(2026,5,30),300.0, Status.IN_PROGRESS, 2);

        List<Task> tasks = List.of(task1, task2);

        when(taskRepository.getAllTasks()).thenReturn(tasks);

        //Act
        List<Task> result = taskService.getAllTasks();

        //Assert
        assertEquals(tasks, result);

       //Assert
        verify(taskRepository, times(1)).getAllTasks();
    }


    @Test
    void deleteTask_shouldCallRepositoryDelete() {

        //Arrange:
        Task task = new Task();
        task.setId(1);

        // Act:
        taskService.deleteTask(task.getId());

        //Assert
        verify(taskRepository, times(1)).deleteTask(1);
    }


    @Test
    void updateTask_shouldCallRepositoryUpdate() {

        //Arrange
        Task task = new Task();
        task.setHourlyRate(100);

        //Act
        taskService.updateTask(task);

        //Assert
        verify(taskRepository, times(1)).updateTask(task);
    }


    @Test
    void getTasksBySubprojectId_shouldReturnTasks_whenSubProjectIdIsValid() {

        //Arrange
        int subProjectId1 = 1;

        Task task1 = new Task(1, "Test Task 1", "Test for Task Service 1",LocalDate.of(2026,5,30), 250.0, Status.TODO, 1);
        Task task2 = new Task(2, "Test Task 2", "Test for Task Service 2",LocalDate.of(2026,5,30), 300.0, Status.IN_PROGRESS, 2);

        List<Task> tasks = List.of(task1, task2);

        when(taskRepository.getTasksBySubProjectId(subProjectId1)).thenReturn(tasks);

        //Act
        List<Task> result = taskService.getTasksBySubProjectId(subProjectId1);

        //Assert
        assertEquals(tasks, result);

        //Assert
        verify(taskRepository, times(1)).getTasksBySubProjectId(subProjectId1);
    }


    @Test
    void getTasksBySubProjectId_shouldThrowException_whenSubProjectIdIsInvalid() {

        //Arrange
        int subProjectId = 0;

       //Act + Assert
        assertThrows(IllegalArgumentException.class, () -> taskService.getTasksBySubProjectId(subProjectId));

       //Assert
        verify(taskRepository, never()).getTasksBySubProjectId(subProjectId);
    }

    @Test
    void getFullTasks_shouldReturnTaskWithSubtasks(){

        //Arrange
        Task task = new Task();
        task.setId(1);

        SubTask subTask = new SubTask();
        subTask.setTaskId(1);

        List<Task> tasks = List.of(task);
        List<SubTask> subTasks = List.of(subTask);

        when(taskRepository.getTasksBySubProjectId(1)).thenReturn(tasks);
        when(subTaskService.calculateEstimatedHours(1)).thenReturn(0);
        when(subTaskService.getSubTasksByTaskId(1)).thenReturn(subTasks);

        //Act
        List<Task> result = taskService.getFullTasks(1);

        //Assert
        assertEquals(1, result.size());
        assertEquals(subTasks, result.get(0).getSubTasks());
        verify(subTaskService, times(1)).getSubTasksByTaskId(1);
    }

    @Test
    void deleteTaskBySubProjectId_shouldReturnDeleteCount_whenSubProjectIdIsValid() {

        //Arrange
        int subProjectId = 1;

        when(taskRepository.deleteTaskBySubProjectId(subProjectId)).thenReturn(1);

        //Act
        int result = taskService.deleteTaskBySubProjectId(subProjectId);

       //Assert
        assertEquals(1, result);

        //Assert
        verify(taskRepository, times(1)).deleteTaskBySubProjectId(subProjectId);
    }


    @Test
    void deleteTaskBySubProjectId_shouldThrowException_whenSubProjectIdIsInvalid() {

        //Arrange
        int subProjectId = 0;

         //Act + Assert
        assertThrows(IllegalArgumentException.class, () -> taskService.deleteTaskBySubProjectId(subProjectId));

        //Assert
        verify(taskRepository, never()).deleteTaskBySubProjectId(subProjectId);
    }


    @Test
    void getFullTasks_shouldReturnTasksWithCorrectTotalPrice() {

        //Arrange
        int subProjectId = 1;
        Task task = new Task(1, "Test Task", "Test", LocalDate.of(2026,5,30),250.0, Status.TODO, 1);


        when(taskRepository.getTasksBySubProjectId(subProjectId)).thenReturn(List.of(task));
        when(subTaskService.calculateEstimatedHours(1)).thenReturn(2);

        //Act
        List<Task> result = taskService.getFullTasks(subProjectId);

        //Assert
        assertEquals(500, result.get(0).getTotalPrice());
        assertEquals(2, result.get(0).getEstimatedHours());
        verify(subTaskService, times(1)).calculateEstimatedHours(1);
    }


    @Test
    void getTaskByIdShouldReturnTaskSuccessfully() {

        //Arrange
        Task task = new Task();
        task.setId(1);

        Mockito.when(taskRepository.findById(task.getId())).thenReturn(task);

        //Act
        taskService.findTaskById(task.getId());

        //Assert
        Mockito.verify(taskRepository).findById((task.getId()));
    }


    @Test
    void findFullTask_shouldReturnTaskWithSubTasks() {

        //Arrange
        Task task = new Task();
        task.setId(1);

        SubTask subTask = new SubTask();
        subTask.setTaskId(1);

        List<SubTask> subTasks = List.of(subTask);


        when(taskRepository.findById(1)).thenReturn(task);
        when(subTaskService.getSubTasksByTaskId(1)).thenReturn(subTasks);

        //Act
        Task result = taskService.findFullTask(1);

        //Assert
        assertEquals(subTasks, result.getSubTasks());
        verify(subTaskService, Mockito.times(1)).getSubTasksByTaskId(1);
    }
}