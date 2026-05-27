package thestudiegruppe.projectestimationtool.Repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import thestudiegruppe.projectestimationtool.Model.Status;
import thestudiegruppe.projectestimationtool.Model.SubTask;

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

        //Act
        List<SubTask> subTasks = subTaskRepository.findAllSubTask();

        //Assert
        assertThat(subTasks)
                .hasSize(2);

        //Assert
        assertThat(subTasks)
                .extracting(SubTask::getName)
                .contains("Test SubTask 1", "Test SubTask 2");
    }

    @Test
    void addSubTask_ShouldSaveNewSubTaskInDatabase(){

        //Arrange
        SubTask subTask = new SubTask();
        subTask.setName("Test SubTask 3");
        subTask.setDescription("SubTask description 3");
        subTask.setEstimatedHours(10);
        subTask.setStatus(Status.DONE);
        subTask.setTaskId(1);

        //Act
        subTaskRepository.addSubTask(subTask);

        //Assert
        List<SubTask> subTasks = subTaskRepository.findAllSubTask();

        //Assert
        assertThat(subTasks).hasSize(3);

        //Assert
        assertThat(subTasks)
                .extracting(SubTask::getName)
                .contains("Test SubTask 3");
    }

    @Test
    void deleteSubTask_ShouldDeleteSubTaskFromDatabase(){

        //Arrange
       int subTaskId = 1;

        //Act
        subTaskRepository.deleteSubTask(subTaskId);

        //Assert
        List<SubTask> subTasks = subTaskRepository.findAllSubTask();

        //Assert
        assertThat(subTasks)
                .extracting(SubTask::getId)
                .doesNotContain(subTaskId);
    }

    @Test
    void findSubTaskById_ShouldReturnSubTask_WhenSubTaskExists(){

        //Arrange
        int subTaskId = 1;

        //Act
        SubTask foundSubTask = subTaskRepository.findSubTaskById(subTaskId);

        //Assert
       assertThat(foundSubTask).isNotNull();

        //Assert
        assertThat(foundSubTask.getId())
                .isEqualTo(subTaskId);
    }

    @Test
    void updateSubTask_ShouldUpdateSubTaskInDatabase(){

        //Arrange
        int subTaskId = 1;

        SubTask subTask = new SubTask();
        subTask.setId(subTaskId);
        subTask.setName("Test SubTask 4");
        subTask.setDescription("SubTask description 4");
        subTask.setEstimatedHours(25);
        subTask.setStatus(Status.IN_PROGRESS);
        subTask.setTaskId(1);

        //Act
        subTaskRepository.updateSubTask(subTask);

        //Assert
        SubTask updatedSubTask = subTaskRepository.findSubTaskById(subTaskId);

        //Assert
        assertThat(updatedSubTask).isNotNull();

        //Assert
        assertThat(updatedSubTask.getId()).isEqualTo(subTaskId);
        assertThat(updatedSubTask.getName()).isEqualTo("Test SubTask 4");
        assertThat(updatedSubTask.getDescription()).isEqualTo("SubTask description 4");
        assertThat(updatedSubTask.getStatus()).isEqualTo(Status.IN_PROGRESS);
        assertThat(updatedSubTask.getEstimatedHours()).isEqualTo(25);
        assertThat(updatedSubTask.getTaskId()).isEqualTo(1);

    }

    @Test
    void findSubTaskByTaskId_ShouldReturnSubTasksForTask(){

        //Arrange
        int taskId = 1;

        //Act
        List<SubTask> foundSubTasks = subTaskRepository.findSubTaskByTaskId(taskId);

        //Assert
        assertThat(foundSubTasks).isNotNull();

        //Assert
        assertThat(foundSubTasks).hasSize(2);

        //Assert
        assertThat(foundSubTasks)
                .extracting(SubTask::getTaskId)
                .containsOnly(taskId);
    }

    @Test
    void deleteSubTaskByTaskId_ShouldDeleteSubTasksForTask(){
        //Arrange
        int taskId = 1;

        //Act
        int deletedSubTask = subTaskRepository.deleteSubTaskByTaskId(taskId);

        //Assert
        List<SubTask> subTaskAfterDelete = subTaskRepository.findSubTaskByTaskId(taskId);

        //Assert
        assertThat(deletedSubTask).isGreaterThan(0);

        //Assert
        assertThat(subTaskAfterDelete).isEmpty();
    }

}
