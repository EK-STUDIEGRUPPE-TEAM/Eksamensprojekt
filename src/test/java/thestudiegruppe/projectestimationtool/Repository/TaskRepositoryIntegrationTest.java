package thestudiegruppe.projectestimationtool.Repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import thestudiegruppe.projectestimationtool.Model.Status;
import thestudiegruppe.projectestimationtool.Model.Task;

import java.time.LocalDate;
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

        //Act
        List<Task> tasks = taskRepository.getAllTasks();

        //Assert
        assertThat(tasks)
                .hasSize(2);

        //Assert
        assertThat(tasks)
                .extracting(Task::getName)
                .contains("Test Task 1", "Test Task 2");
    }

    @Test
    void addTask_ShouldSaveNewTaskInDatabase(){

        //Arrange
        Task task = new Task();
        task.setName("Test Task 3");
        task.setDescription("Task description 3");
        task.setDeadline(LocalDate.of(2027,5, 22));
        task.setStatus(Status.DONE);
        task.setHourlyRate(100.0);
        task.setSubProjectId(1);

        //Act
        taskRepository.addTask(task);

        //Assert
        List<Task> tasks = taskRepository.getAllTasks();

        //Assert
        assertThat(tasks).hasSize(3);

        //Assert
        assertThat(tasks)
                .extracting(Task::getName)
                .contains("Test Task 3");
    }

    @Test
    void deleteTask_ShouldDeleteTaskFromDatabase(){

        //Arrange
        int taskId = 1;

        //Act
        taskRepository.deleteTask(taskId);

        //Assert
        List<Task> tasks = taskRepository.getAllTasks();

        //Assert
        assertThat(tasks)
                .extracting(Task::getId)
                .doesNotContain(taskId);
    }

    @Test
    void findTaskById_ShouldReturnTask_WhenTaskExists(){

        //Arrange
        int taskId = 1;

        //Act
        Task foundTask = taskRepository.findById(taskId);

        //Assert
        assertThat(foundTask).isNotNull();

        //Assert
        assertThat(foundTask.getId())
                .isEqualTo(taskId);
    }

    @Test
    void updateTask_ShouldUpdateTaskInDatabase(){

        //Arrange
        int taskId = 1;

        //Arrange
        Task task = new Task();
        task.setId(taskId);
        task.setName("Test Task 4");
        task.setDescription("Task description 4");
        task.setDeadline(LocalDate.of(2027,5, 22));
        task.setStatus(Status.IN_PROGRESS);
        task.setHourlyRate(500.0);
        task.setSubProjectId(1);

        //Act
        taskRepository.updateTask(task);

        //Assert
        Task updatedTask = taskRepository.findById(taskId);

        //Assert
        assertThat(updatedTask).isNotNull();

        //Assert
        assertThat(updatedTask.getId()).isEqualTo(taskId);
        assertThat(updatedTask.getName()).isEqualTo("Test Task 4");
        assertThat(updatedTask.getDescription()).isEqualTo("Task description 4");
        assertThat(updatedTask.getDeadline()).isEqualTo(LocalDate.of(2027,5, 22));
        assertThat(updatedTask.getStatus()).isEqualTo(Status.IN_PROGRESS);
        assertThat(updatedTask.getHourlyRate()).isEqualTo(500.0);
        assertThat(updatedTask.getSubProjectId()).isEqualTo(1);
    }

    @Test
    void findTaskBySubProjectId_ShouldReturnTasksForSubProject(){

        //Arrange
        int subProjectId = 1;

        //Act
        List<Task> foundTasks = taskRepository.getTasksBySubProjectId(subProjectId);

        //Assert
        assertThat(foundTasks).isNotNull();

        //Assert
        assertThat(foundTasks).hasSize(2);

        //Assert
        assertThat(foundTasks)
                .extracting(Task::getSubProjectId)
                .containsOnly(subProjectId);
    }

    @Test
    void deleteTaskBySubProjectId_ShouldDeleteTasksForSubProject(){

        //Arrange
        int subProjectId = 1;

        //Act
        int deletedTask = taskRepository.deleteTaskBySubProjectId(subProjectId);

        //Assert
        List<Task> taskAfterDelete = taskRepository.getTasksBySubProjectId(subProjectId);


        //Assert
        assertThat(deletedTask).isGreaterThan(0);

        //Assert
        assertThat(taskAfterDelete).isEmpty();
    }

}
