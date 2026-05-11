package thestudiegruppe.projectestimationtool.Service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import thestudiegruppe.projectestimationtool.Model.Status;
import thestudiegruppe.projectestimationtool.Model.Task;
import thestudiegruppe.projectestimationtool.Repository.TaskRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static thestudiegruppe.projectestimationtool.Model.Status.TODO;

//@SpringBootTest
//@ActiveProfiles("test")
//@Sql (scripts = "classpath:h2init.sql")

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository repository;

    @InjectMocks
    private TaskService service;

    @Test
    void createTask_thenReturnOneWhenSucceed(){
        Task task = new Task(1, "Test Task", "Test for Task Service", 250.0, TODO, 2);
        when(repository.addTask(task)).thenReturn(1);

        int result = service.createTask(task);

        assertEquals(1, result);
    }

    @Test
    void getAllTasksShouldReturnTasks(){
        Task task = new Task(1, "Test Task", "Test for Task Service", 250.0, TODO, 2);

        when(repository.findAll()).thenReturn(List.of(task));

        List<Task> result = service.getAllTasks();

        assertEquals(1, result.size());
    }

}