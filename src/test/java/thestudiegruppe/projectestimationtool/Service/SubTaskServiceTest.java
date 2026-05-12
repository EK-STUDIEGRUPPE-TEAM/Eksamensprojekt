package thestudiegruppe.projectestimationtool.Service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import thestudiegruppe.projectestimationtool.Model.SubTask;
import thestudiegruppe.projectestimationtool.Model.Task;
import thestudiegruppe.projectestimationtool.Repository.SubTaskRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static thestudiegruppe.projectestimationtool.Model.Status.*;

@ExtendWith(MockitoExtension.class)
class SubTaskServiceTest {

    @Mock
    private SubTaskRepository repository;

    @InjectMocks
    private SubTaskService service;

    // tester om calculate metoden virker (DEN GIVER NULL HVIS DER IKKE ER LAVET SUBTASKS, IKKE 0)

//    @Test
//    void calculateEstimatedHoursShouldReturnTotal(){
//        Task task = new Task(1, "Test Task", "Test for Task Service", 250.0, TODO, 2);
//
//        int result = service.calculateEstimatedHours(task);
//
//        assertNull(result);
//    }

    // tester om calculate metoden virker når man finder et ikke finder ID (GIVER IKKE NULL MODSAT 'Task task')

    @Test
    void calculateEstimatedHoursShouldReturnTotal(){
        when(repository.findSubTaskByTaskId(1)).thenReturn(List.of(
//                new SubTask(1, "Test subtask", "test", 120, DONE, 1)
        ));

        int result = service.calculateEstimatedHours(1);

        assertEquals(0, result);
    }



}