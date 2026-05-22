package thestudiegruppe.projectestimationtool.Service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import thestudiegruppe.projectestimationtool.Model.Status;
import thestudiegruppe.projectestimationtool.Model.SubTask;
import thestudiegruppe.projectestimationtool.Repository.SubTaskRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = BEFORE_TEST_METHOD)
public class SubTaskRepositoryIntegrationTest {

    @Autowired
    private SubTaskRepository subTaskRepository;

    @Test
    void getAllSubTasks_ShouldReturnSubTasksFromDatabase(){
        //Arrange: Vi henter som regel bare den data der allerede ligger inde i h2init.sql.

        //Act: Henter alle subTasks fra h2-databasen.
        List<SubTask> subTasks = subTaskRepository.findAllSubTask();

        //Assert: Vi tjekker, at h2init.sql har oprettet 2 subTasks.
        assertThat(subTasks)
                .hasSize(2);

        //Assert: Tjekker at subTaskne fra h2init.sql findes.
        assertThat(subTasks)
                .extracting(SubTask::getName)
                .contains("Test SubTask 1", "Test SubTask 2");
    }

    @Test
    void addSubTask_ShouldSaveNewSubTaskInDatabase(){
        //Arrange: Vi laver en ny subTask og indsætter data.
        SubTask subTask = new SubTask();
        subTask.setName("Test SubTask 3");
        subTask.setDescription("SubTask description 3");
        subTask.setEstimatedHours(10);
        subTask.setStatus(Status.DONE);
        subTask.setTaskId(1);

        //Act: Gemmer den nye subTask i h2-databasen.
        subTaskRepository.addSubTask(subTask);

        //Assert: Henter alle subTaskne igen efter add.
        List<SubTask> subTasks = subTaskRepository.findAllSubTask();

        //Assert: Tjekker at der nu er 3 subTasks efter add.
        assertThat(subTasks).hasSize(3);

        //Assert: Tjekker at den ny subTask findes i databasen.
        assertThat(subTasks)
                .extracting(SubTask::getName)
                .contains("Test SubTask 3");
    }

    @Test
    void deleteSubTask_ShouldDeleteSubTaskFromDatabase(){
        //Arrange: Vi vælger et subTaskId, som allerede findes i h2init.sql.
       int subTaskId = 1;

        //Act: Sletter subTasken med det valgte id.
        subTaskRepository.deleteSubTask(subTaskId);

        //Assert: Henter alle subTasks efter delete.
        List<SubTask> subTasks = subTaskRepository.findAllSubTask();

        //Assert: tjekker at subTasken id ikke længere findes.
        assertThat(subTasks)
                .extracting(SubTask::getId)
                .doesNotContain(subTaskId);
    }

    @Test
    void findSubTaskById_ShouldReturnSubTask_WhenSubTaskExists(){
        //Arrange: Vi vælger et subTaskId, som allerede findes i h2init.sql.
        int subTaskId = 1;

        //Act: Finder en subTask ud fra id.
        SubTask foundSubTask = subTaskRepository.findSubTaskById(subTaskId);

        //Assert: Tjekker at der faktisk blev fundet en subTask.
       assertThat(foundSubTask).isNotNull();

        //Assert: Tjekker at subTaskens id matcher det id, vi søgte efter.
        assertThat(foundSubTask.getId())
                .isEqualTo(subTaskId);
    }

    @Test
    void updateSubTask_ShouldUpdateSubTaskInDatabase(){
        //Arrange: Vi vælger et subTaskId, som allerede findes i h2init.sql.
        int subTaskId = 1;

        SubTask subTask = new SubTask();
        subTask.setId(subTaskId);
        subTask.setName("Test SubTask 4");
        subTask.setDescription("SubTask description 4");
        subTask.setEstimatedHours(25);
        subTask.setStatus(Status.IN_PROGRESS);
        subTask.setTaskId(1);

        //Act: Opdaterer subTasken i h2-databasen.
        subTaskRepository.updateSubTask(subTask);

        //Assert: Henter subTasken igen og tjekker de nye værdier.
        SubTask updatedSubTask = subTaskRepository.findSubTaskById(subTaskId);

        //Assert: Tjekker at subTasken stadig findes efter update.
        assertThat(updatedSubTask).isNotNull();

        //Assert: Tjekker at subTasken har de nye værdier fra update.
        assertThat(updatedSubTask.getId()).isEqualTo(subTaskId);
        assertThat(updatedSubTask.getName()).isEqualTo("Test SubTask 4");
        assertThat(updatedSubTask.getDescription()).isEqualTo("SubTask description 4");
        assertThat(updatedSubTask.getStatus()).isEqualTo(Status.IN_PROGRESS);
        assertThat(updatedSubTask.getEstimatedHours()).isEqualTo(25);
        assertThat(updatedSubTask.getTaskId()).isEqualTo(1);

    }

    @Test
    void findSubTaskByTaskId_ShouldReturnSubTasksForTask(){
        //Arrange: Vi vælger et taskId, som allerede har subTask i h2init.sql.
        int taskId = 1;

        //Act: Vi henter alle subTasks, der tilhører taskId 1.
        List<SubTask> foundSubTasks = subTaskRepository.findSubTaskByTaskId(taskId);

        //Assert: Vi tjekker, at listen ikke er null.
        assertThat(foundSubTasks).isNotNull();

        //Assert: Vi tjekker, at der er 2 subTasks for taskId 1.
        assertThat(foundSubTasks).hasSize(2);

        //Assert: Vi tjekker, at alle subTasks faktisk tilhører taskId 1.
        assertThat(foundSubTasks)
                .extracting(SubTask::getTaskId)
                .containsOnly(taskId);
    }

    @Test
    void deleteSubTaskByTaskId_ShouldDeleteSubTasksForTask(){
        //Arrange: Vi vælger et taskId, som allerede har subTasks i h2init.sql.
        int taskId = 1;

        //Act: Vi sletter alle subTasks, der hører til taskId.
        int deletedSubTask = subTaskRepository.deleteSubTaskByTaskId(taskId);

        //Assert: Vi henter subTasks for samme taskId igen efter delete.
        List<SubTask> subTaskAfterDelete = subTaskRepository.findSubTaskByTaskId(taskId);

        //Assert: Tjekker at der faktisk blev slettet mindst en subTask.
        assertThat(deletedSubTask).isGreaterThan(0);

        //Assert: Tjekker at der ikke længere findes subTasks for taskId.
        assertThat(subTaskAfterDelete).isEmpty();
    }

}
