package thestudiegruppe.projectestimationtool.Service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import thestudiegruppe.projectestimationtool.Exception.NegativeValueException;
import thestudiegruppe.projectestimationtool.Model.SubTask;
import thestudiegruppe.projectestimationtool.Repository.SubTaskRepository;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static thestudiegruppe.projectestimationtool.Model.Status.*;

@ExtendWith(MockitoExtension.class)
class SubTaskServiceTest {

    // Laver falsk repository af SubTaskRepository
    @Mock
    private SubTaskRepository repository;

    // Injecter falsk repository i vores rigtige service klasse så man ikke behøver en rigtig database for test
    @InjectMocks
    private SubTaskService service;

    @Test
    void createSubTask_ShouldCallRepository() {
        // ARRANGE
        // Fake objekt som testdata
        SubTask subTask = new SubTask();

        // ACT
        // Her kalder vi på service metoden
        service.createSubTask(subTask);

        // ASSERT
        /* Tjekker om service sender objektet til repository
        Hvis den gør så hænger de sammen
         */
        verify(repository, times(1)).addSubTask(subTask);
    }

    @Test
    void getAllSubTasks_ShouldReturnAllSubTasks() {
        //ARRANGE
        // Her laver vi vores fake testdata
        SubTask subTask = new SubTask();
        SubTask subTask1 = new SubTask();

        /* Vi bruger variablen subTasks til at holde objekterne
           ved at bruge List.of()      */
        List<SubTask> subTasks = List.of(subTask, subTask1);

        //ACT
        /* Her bruger vi when().thenReturn til at få vores repository
        til at returnere vores variabel aka vores liste med objekter
         */
        when(repository.findAllSubTask()).thenReturn(subTasks);

        // Så tilkobler vi service metoden til en variabel
        List<SubTask> result = service.getAllSubTasks();

        // ASSERT
        /* Nu tester vi så om result og subTasks har samme liste
        Hvis den virker så hænger metoderne sammen
        Og så virker getAllSubTask
         */
        assertEquals(result, subTasks);

        // Her bruger vi verify til at teste om metoden kører 1 gang
        verify(repository, times(1)).findAllSubTask();
    }

    @Test
    void getSubTaskByTaskId_ShouldReturnSubTaskId() {
        // ARRANGE
        // Fake objekt som testdata
        SubTask subTask = new SubTask();

        // Vi bruger en variabel til at holde dataen
        List<SubTask> subTasks = List.of(subTask, subTask);

        // ACT
        /* Her bruger vi when().thenReturn til at få vores repository
        til at returnere vores variabel aka vores liste med objekter
        */
        when(repository.findSubTaskByTaskId(1)).thenReturn(subTasks);

        // Tilkobler service metode til variabel
        List<SubTask> result = service.getSubTasksByTaskId(1);

        // ASSERT
        // Tester at repository og service får samme liste
        assertEquals(subTasks, result);
    }

    @Test
    void deleteSubTask_ShouldCallRepository() {
        // ARRANGE
        // Fake id som testdata
        int id = 1;

        // ACT
        // Her kalder vi på service metoden
        service.deleteSubTask(id);

        // ASSERT
        /* Tjekker om service sender id til repository
        Hvis den gør så hænger de sammen
        */
        verify(repository, times(1)).deleteSubTask(id);
    }

    @Test
    void updateSubTask_ShouldCallRepository() {
        // ARRANGE
        // Ett fake objekt som testdata
        SubTask subTask = new SubTask();

        // ACT
        // Her kalder vi på service metoden
        service.updateTask(subTask);

        // ASSERT
        /* Tjekker om service sender objektet til repository
        Hvis den gør så hænger de sammen
        */
        verify(repository, times(1)).updateSubTask(subTask);
    }

    @Test
    void deleteSubTaskByTaskId_ShouldCallRepository() {
        // ARRANGE
        // Fake id som testdata
        int taskId = 1;

        // ACT
        // Her kalder vi på service metoden
        service.deleteSubTaskByTaskId(taskId);

        // ASSERT
        /* Tjekker om service sender taskId til repository
        Hvis den gør så hænger de sammen
        */
        verify(repository, times(1)).deleteSubTaskByTaskId(taskId);
    }

    // tester om calculate metoden virker når man ikke finder ID (GIVER IKKE NULL)
    @Test
    void calculateEstimatedHours_ShouldReturnTotal() {
        //ARRANGE
        // Fake objekter med hver deres estimatedHours til testdata
        List<SubTask> subTasks = List.of(
                new SubTask(1, "Test subtask", "test", 120, DONE, 1),
                new SubTask(2, "Test subtask2", "test", 380, DONE, 1)
        );

        // ACT
        /* when().thenReturn gør vores repository returnerer
        vores variabel der indeholder liste med objekter
         */
        when(repository.findSubTaskByTaskId(1)).thenReturn(subTasks);
        int result = service.calculateEstimatedHours(1);

        // ASSERT
        // Tester at den totale sum af timer er korrekt
        assertEquals(500, result);
    }

    @Test
    void calculateEstimatedHours_ShouldThrowNegativeValueException_WhenHoursAreNegative() {
        // ARRANGE
        // Fake testdata med negative timer
        List<SubTask> subTasks = List.of(
                new SubTask(1, "Test", "Test desc", -10, DONE, 1)
        );

        // ACT
        /* Her bruger vi when().thenReturn til at få vores repository
        til at returnere vores variabel med testdataen
        */
        when(repository.findSubTaskByTaskId(1)).thenReturn(subTasks);

        // ASSERT
        // Tester at negative timer kaster en custom NegativeValueException
        assertThrows(NegativeValueException.class, () -> service.calculateEstimatedHours(1));
    }


}