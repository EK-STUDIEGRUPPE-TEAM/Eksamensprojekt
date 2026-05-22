package thestudiegruppe.projectestimationtool.Service.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
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
    void getAllTasks(){
        //Arrange: Vi henter som regel bare den data der allerede ligger inde i h2init.sql.


        //Act: Henter alle projekter fra H2-databasen.
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
    void addTask(){
        
    }

}
