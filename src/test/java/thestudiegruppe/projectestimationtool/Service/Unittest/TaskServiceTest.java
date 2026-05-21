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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


/* Den her annotation gør at Mockito virker i testen.
Uden den kan @Mock og @InjectMocks ikke rigtigt arbejde ordentligt
og derfor hjælper dem med det. */
@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    //Lav en falsk version af UserRepository.
    @Mock
    private TaskRepository taskRepository;

    @Mock
    private SubTaskService subTaskService;

    /* Lav en rigtig UserService, og sæt den falske userRepository ind i den.
     Så man kan teste service-klassen, men uden en rigtig database. */
    @InjectMocks
    private TaskService taskService;


    //Tester at en task bliver gemt korrekt, når objekter ikke er null.
    @Test
    void createTask_shouldCallRepositoryCreateTask_whenTaskIsNotNull() {
        //Arrange: Der oprettes en ny Task, som ikke er null.
        Task task = new Task();

        //Act: Metoden bliver kaldt.
        taskService.createTask(task);

        //Assert: Testen tjekker, at add metoden bliver kaldt 1 gang.
        verify(taskRepository, times(1)).addTask(task);
    }


    //Tester at systemet giver fejl, hvis man prøver at oprette en task med null.
    @Test
    void createTask_shouldThrowException_whenTaskIsNull() {
        //Arrange: Vi laver en ugyldig Task, som er null.
        Task task = null;

        /* Act + Assert: Vi forventer, at metoden kaster en
        IllegalArgumentException, når vi prøver at oprette en null-task. */
        assertThrows(IllegalArgumentException.class, () -> taskService.createTask(task));

        /* Assert: Vi tjekker også, at repository aldrig blev kaldt,
        fordi metoden skal stoppe før den når dertil. */
        verify(taskRepository, never()).addTask(any(Task.class));
    }

    // Tester at service-klassen returnerer alle tasks fra repository.
    @Test
    void getAllTasksShouldReturnTasks() {
        // Arrange: Vi laver 2 test-Tasks.
        Task task1 = new Task(1, "Test Task 1", "Test for Task Service 1", 250.0, Status.TODO, 1);
        Task task2 = new Task(2, "Test Task 2", "Test for Task Service 2", 300.0, Status.IN_PROGRESS, 2);

        /* Vi samler dem i en liste, som repository skal returnere,
        når getAllTasks() bliver kaldt. */
        List<Task> tasks = List.of(task1, task2);

        when(taskRepository.getAllTasks()).thenReturn(tasks);

        //Act: Vi kalder service-metoden
        List<Task> result = taskService.getAllTasks();

        /* Assert: Vi tjekker, at resultatet fra service-metoden
        er den samme liste, som repository returnerede. */
        assertEquals(tasks, result);

        // Vi tjekker også, at repository-metoden blev kaldt præcis 1 gang.
        verify(taskRepository, times(1)).getAllTasks();
    }

    // Tester at en task bliver slettet korrekt ud fra taskens id.
    @Test
    void deleteTask_shouldCallRepositoryDelete() {
        // Arrange: Vi laver en Task og giver den et id.
        Task task = new Task();
        task.setId(1);

        // Act: Vi kalder deleteTask() med taskens id.
        taskService.deleteTask(task.getId());

        /* Assert: Vi tjekker, at repository-metoden deleteTask(1)
        blev kaldt præcis 1 gang. */
        verify(taskRepository, times(1)).deleteTask(1);
    }

    // Tester at en task bliver opdateret korrekt i repository.
    @Test
    void updateTask_shouldCallRepositoryUpdate() {
        // Arrange: Vi laver en Task, som kan opdateres.
        Task task = new Task();

        // Act: Vi kalder updateSubTask() i service-klassen.
        taskService.updateTask(task);

        /* Assert: Vi tjekker, at repository-metoden updateSubTask(task)
        blev kaldt præcis 1 gang. */
        verify(taskRepository, times(1)).updateTask(task);


    }

    // Tester at service-klassen returnerer en liste af tasks, når subProjectId er gyldigt.
    @Test
    void getTasksBySubprojectId_shouldReturnTasks_whenSubProjectIdIsValid() {
        // Arrange: Vi laver et gyldigt subProjectId.
        int subProjectId1 = 1;

        // Vi laver 2 test-Tasks.
        Task task1 = new Task(1, "Test Task 1", "Test for Task Service 1", 250.0, Status.TODO, 1);
        Task task2 = new Task(2, "Test Task 2", "Test for Task Service 2", 300.0, Status.IN_PROGRESS, 2);

        /* Vi samler tasks i en liste, som repository skal returnere,
        når getTasksBySubProjectId(subProjectId1) bliver kaldt. */
        List<Task> tasks = List.of(task1, task2);

        when(taskRepository.getTasksBySubProjectId(subProjectId1)).thenReturn(tasks);

        // Act: Vi kalder service-metoden.
        List<Task> result = taskService.getTasksBySubProjectId(subProjectId1);

        /* Assert: Vi tjekker, at resultatet er den samme liste,
        som repository returnerede. */
        assertEquals(tasks, result);

        // Vi tjekker også, at repository-metoden blev kaldt præcis 1 gang.
        verify(taskRepository, times(1)).getTasksBySubProjectId(subProjectId1);

    }

    // Tester at systemet giver fejl, hvis subProjectId ikke er gyldigt.
    @Test
    void getTasksBySubProjectId_shouldThrowException_whenSubProjectIdIsInvalid() {
        // Arrange: Vi laver et ugyldigt subProjectId.
        int subProjectId = 0;

        /* Act + Assert: Vi forventer, at metoden kaster en
        IllegalArgumentException, fordi id'et ikke er gyldigt. */
        assertThrows(IllegalArgumentException.class, () -> taskService.getTasksBySubProjectId(subProjectId));

        /* Assert: Vi tjekker også, at repository-metoden aldrig blev kaldt,
        fordi servicen skal stoppe før det. */
        verify(taskRepository, never()).getTasksBySubProjectId(subProjectId);
    }

    // Tester at getFullTasks returnerer en task med de korrekte subtasks sat på.
    @Test
    void getFullTasks_shouldReturnTaskWithSubtasks(){
        // Arrange: Vi laver et test task objekt og en test subtask der hører til den
        Task task = new Task();
        task.setId(1);

        SubTask subTask = new SubTask();
        subTask.setTaskId(1);

        List<Task> tasks = List.of(task);
        List<SubTask> subTasks = List.of(subTask);

        when(taskRepository.getTasksBySubProjectId(1)).thenReturn(tasks);

        // subTaskService returnerer subtasklisten og 0 estimerede timer
        when(subTaskService.calculateEstimatedHours(1)).thenReturn(0);
        when(subTaskService.getSubTasksByTaskId(1)).thenReturn(subTasks);

        // Act: Vi kalder service metoden
        List<Task> result = taskService.getFullTasks(1);

        /* Assert: Vi tjekker at listen indeholder 1 task,
        og at subtasklisten er sat korrekt på task-objektet */
        assertEquals(1, result.size());
        assertEquals(subTasks, result.get(0).getSubTasks());
        verify(subTaskService, times(1)).getSubTasksByTaskId(1);
    }

    // Tester at tasks bliver slettet korrekt ud fra et gyldigt subProjectId, og at antal slettede tasks returneres.
    @Test
    void deleteTaskBySubProjectId_shouldReturnDeleteCount_whenSubProjectIdIsValid() {
        // Arrange: Vi laver et gyldigt subProjectId.
        int subProjectId = 1;

        /* Vi bestemmer, at repository skal returnere 1,
        som betyder at 1 række/task blev slettet. */
        when(taskRepository.deleteTaskBySubProjectId(subProjectId)).thenReturn(1);

        // Act: Vi kalder service-metoden.
        int result = taskService.deleteTaskBySubProjectId(subProjectId);

        /* Assert: Vi tjekker, at resultatet er 1,
        altså at der blev slettet 1 task. */
        assertEquals(1, result);

        // Vi tjekker også, at repository-metoden blev kaldt præcis 1 gang.
        verify(taskRepository, times(1)).deleteTaskBySubProjectId(subProjectId);

    }

    // Tester at systemet giver fejl, hvis man prøver at slette tasks med et ugyldigt subProjectId.
    @Test
    void deleteTaskBySubProjectId_shouldThrowException_whenSubProjectIdIsInvalid() {
        // Arrange: Vi laver et ugyldigt subProjectId.
        int subProjectId = 0;

         /* Act + Assert: Vi forventer, at metoden kaster en
        IllegalArgumentException, når id'et er ugyldigt. */
        assertThrows(IllegalArgumentException.class, () -> taskService.deleteTaskBySubProjectId(subProjectId));

        /* Assert: Vi tjekker, at repository-metoden aldrig blev kaldt,
        fordi servicen skal stoppe med det samme. */
        verify(taskRepository, never()).deleteTaskBySubProjectId(subProjectId);

    }

    // Tester at getFullTasks beregner totalprisen korrekt for hver task.
    @Test
    void getFullTasks_shouldReturnTasksWithCorrectTotalPrice() {
        // Arrange: Vi laver et gyldigt subProjectId og en test-Task.
        int subProjectId = 1;
        Task task = new Task(1, "Test Task", "Test", 250.0, Status.TODO, 1);

        // Repository returnerer task når getTasksBySubProjectId kaldes.
        when(taskRepository.getTasksBySubProjectId(subProjectId)).thenReturn(List.of(task));

        // SubTaskService returnerer 2 estimerede timer for task 1.
        when(subTaskService.calculateEstimatedHours(1)).thenReturn(2);

        // Act: Vi kalder service-metoden.
        List<Task> result = taskService.getFullTasks(subProjectId);

    /* Assert: Vi tjekker at totalprisen er beregnet korrekt.
    Udregning: 250 * 2 = 500 */
        assertEquals(500, result.get(0).getTotalPrice());
        assertEquals(2, result.get(0).getEstimatedHours());
        verify(subTaskService, times(1)).calculateEstimatedHours(1);
    }


    @Test
    void getTaskByIdShouldReturnTaskSuccessfully() {

        //Arrange: Lave testdata
        Task task = new Task();
        task.setId(1);

        Mockito.when(taskRepository.findById(task.getId())).thenReturn(task);

        //Act: Kalde metoden som testes fra serviceklassen

        taskService.findTaskById(task.getId());

        //Assert: Kalder funktionen i service klassen, repository korrekt

        Mockito.verify(taskRepository).findById((task.getId()));

    }





}