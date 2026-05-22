package thestudiegruppe.projectestimationtool.Service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import thestudiegruppe.projectestimationtool.Model.Status;
import thestudiegruppe.projectestimationtool.Model.Task;
import thestudiegruppe.projectestimationtool.Repository.TaskRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = BEFORE_TEST_METHOD)
public class TaskRepositoryIntegrationTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void getAllTasks_ShouldReturnTasksFromDatabase(){
        //Arrange: Vi henter som regel bare den data der allerede ligger inde i h2init.sql.


        //Act: Henter alle tasks fra H2-databasen.
        List<Task> tasks = taskRepository.getAllTasks();

        //Assert: Vi tjekker, at h2init.sql har oprettet 2 tasks.
        assertThat(tasks)
                .hasSize(2);

        //Assert: Tjekker at testTaskne fra H2init.sql findes.
        assertThat(tasks)
                .extracting(Task::getName)
                .contains("Test Task 1", "Test Task 2");
    }

    @Test
    void addTask_ShouldSaveNewTaskInDatabase(){
        //Arrange: Vi laver en ny task og indsætter data.
        Task task = new Task();
        task.setName("Test Task 3");
        task.setDescription("Task description 3");
        task.setStatus(Status.DONE);
        task.setHourlyRate(100.0);
        task.setSubProjectId(1);

        //Act: Gemmer den nye task i H2-databasen.
        taskRepository.addTask(task);

        //Assert: Henter alle tasks igen efter add.
        List<Task> tasks = taskRepository.getAllTasks();

        //Assert: Tjekker at der nu er 3 tasks efter add.
        assertThat(tasks).hasSize(3);

        //Assert: Tjekker at den nye task findes i databasen.
        assertThat(tasks)
                .extracting(Task::getName)
                .contains("Test Task 3");
        
    }

    @Test
    void deleteTask_ShouldDeleteTaskFromDatabase(){
        //Arrange: Vi vælger et taskId, som allerede findes i h2init.sql.
        int taskId = 1;

        //Act: Sletter tasken med det valgte id.
        taskRepository.deleteTask(taskId);

        //Assert: Henter alle tasks efter delete.
        List<Task> tasks = taskRepository.getAllTasks();

        //Assert: tjekker at taskets id ikke længere findes.
        assertThat(tasks)
                .extracting(Task::getId)
                .doesNotContain(taskId);
    }

    @Test
    void findTaskById_ShouldReturnTask_WhenTaskExists(){
        //Arrange: Vi vælger et taskId, som allerede findes i h2init.sql.
        int taskId = 1;

        //Act: Finder en task ud fra id.
        Task foundTask = taskRepository.findById(taskId);

        //Assert: Tjekker at der faktisk blev fundet en task.
        assertThat(foundTask).isNotNull();

        //Assert: Tjekker at taskens id matcher det id, vi søgte efter.
        assertThat(foundTask.getId())
                .isEqualTo(taskId);
    }

    @Test
    void updateTask_ShouldUpdateTaskInDatabase(){
        //Arrange: Vi vælger et taskId, som allerede findes i h2init.sql.
        int taskId = 1;

        //Arrange
        Task task = new Task();
        task.setId(taskId);
        task.setName("Test Task 4");
        task.setDescription("Task description 4");
        task.setStatus(Status.IN_PROGRESS);
        task.setHourlyRate(500.0);
        task.setSubProjectId(1);

        //Act: Opdaterer tasken i h2-databasen.
        taskRepository.updateTask(task);

        //Assert: Henter tasken igen og tjekker de nye værdier.
        Task updatedTask = taskRepository.findById(taskId);

        //Assert: Tjekker at tasken stadig findes efter update.
        assertThat(updatedTask).isNotNull();

        //Assert: Tjekker at tasken har de nye værdier fra update.
        assertThat(updatedTask.getId()).isEqualTo(taskId);
        assertThat(updatedTask.getName()).isEqualTo("Test Task 4");
        assertThat(updatedTask.getDescription()).isEqualTo("Task description 4");
        assertThat(updatedTask.getStatus()).isEqualTo(Status.IN_PROGRESS);
        assertThat(updatedTask.getHourlyRate()).isEqualTo(500.0);
        assertThat(updatedTask.getSubProjectId()).isEqualTo(1);
    }

    @Test
    void findTaskBySubProjectId_ShouldReturnTasksForSubProject(){
        //Arrange: Vi vælger et subProjectId, som allerede har tasks i h2init.sql.
        int subProjectId = 1;

        //Act: Vi henter alle tasks, der tilhører subProjectId 1.
        List<Task> foundTasks = taskRepository.getTasksBySubProjectId(subProjectId);

        //Assert: Vi tjekker, at listen ikke er null.
        assertThat(foundTasks).isNotNull();

        //Assert: Vi tjekker, at der er 2 tasks for subProjectId 1.
        assertThat(foundTasks).hasSize(2);

        //Assert: Vi tjekker, at alle tasks faktisk tilhører subProjectId 1.
        assertThat(foundTasks)
                .extracting(Task::getSubProjectId)
                .containsOnly(subProjectId);
    }

    @Test
    void deleteTaskBySubProjectId_ShouldDeleteTasksForSubProject(){
        //Arrange: Vi vælger et subProjectId, som allerede har tasks i h2init.sql.
        int subProjectId = 1;

        //Act: Vi sletter alle tasks, der hører til subProjectId.
        int deletedTask = taskRepository.deleteTaskBySubProjectId(subProjectId);

        // Assert: Vi henter tasks for samme subProjectId igen efter delete.
        List<Task> taskAfterDelete = taskRepository.getTasksBySubProjectId(subProjectId);


        //Assert: Tjekker at der faktisk blev slettet mindst én task.
        assertThat(deletedTask).isGreaterThan(0);

        //Assert: Tjekker at der ikke længere findes tasks for subProjectId.
        assertThat(taskAfterDelete).isEmpty();
    }

}
