package thestudiegruppe.projectestimationtool.Service.Unittest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import thestudiegruppe.projectestimationtool.Exception.NegativeValueException;
import thestudiegruppe.projectestimationtool.Model.SubTask;
import thestudiegruppe.projectestimationtool.Repository.SubTaskRepository;
import thestudiegruppe.projectestimationtool.Service.SubTaskService;

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
        subTask.setEstimatedHours(10);

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
    void getSubtaskById_ShouldReturnSubTaskSuccessfully() {

        //Arrange: Lave testdata
        SubTask subTask = new SubTask();
        subTask.setId(1);

        when(repository.findSubTaskById(subTask.getId())).thenReturn(subTask);

        //Act: Kalde metoden som testes fra serviceklassen

        service.getSubTaskById(subTask.getId());

        //Assert: Kalder funktionen i service klassen, repository korrekt

        verify(repository).findSubTaskById((subTask.getId()));
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
        subTask.setEstimatedHours(10);

        // ACT
        // Her kalder vi på service metoden
        service.updateSubTask(subTask);

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
    void createSubTask_ShouldThrowNegativeValueException_WhenHoursAreNegative() {
        // ARRANGE
        // Fake testdata med negative timer
        SubTask subTask = new SubTask(1, "Test", "Test desc", -10, DONE, 1);

        // ASSERT
        // Tester at negative timer kaster en custom NegativeValueException ved oprettelse
        assertThrows(NegativeValueException.class, () -> service.createSubTask(subTask));
    }

    @Test
    void updateSubTask_ShouldThrowNegativeValueException_WhenHoursAreNegative() {
        // ARRANGE
        // Fake testdata med negative timer
        SubTask subTask = new SubTask(1, "Test", "Test desc", -10, DONE, 1);

        // ASSERT
        // Tester at negative timer kaster en custom NegativeValueException ved opdatering
        assertThrows(NegativeValueException.class, () -> service.updateSubTask(subTask));
    }


}