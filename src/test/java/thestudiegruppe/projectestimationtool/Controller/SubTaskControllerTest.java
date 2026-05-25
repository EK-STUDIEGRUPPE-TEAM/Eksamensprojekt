package thestudiegruppe.projectestimationtool.Controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import thestudiegruppe.projectestimationtool.Model.Status;
import thestudiegruppe.projectestimationtool.Model.SubTask;
import thestudiegruppe.projectestimationtool.Service.SubTaskService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Tester kun SubTaskController og web-laget, ikke service/repository/database.
@WebMvcTest(SubTaskController.class)
public class SubTaskControllerTest {

    // Bruges til at sende fake HTTP-requests til controlleren.
    @Autowired
    private MockMvc mockMvc;

    // SubTaskService mockes, fordi vi kun tester controllerens flow.
    @MockitoBean
    private SubTaskService subTaskService;

    @Test
    void shouldShowAddSubTaskForm_WhenUserIsLoggedIn() throws Exception{
        // Arrange: Vi simulerer en bruger, der er logget ind.

        // Act + Assert: Vi kalder addsubtask-endpointet og tjekker view + model.
        mockMvc.perform(get("/subtask/addsubtask/1")
                .sessionAttr("userId", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("addsubtask"))
                .andExpect(model().attributeExists("subtask"))
                .andExpect(model().attributeExists("taskId"));

    }

    @Test
    void shouldRedirectToLogin_WhenAddSubTaskWithoutLogin() throws Exception{
        // Arrange: Vi giver ikke session med, fordi brugeren ikke er logget ind.

        // Act + Assert: Vi kalder addsubtask-endpointet og tjekker redirect til login.
        mockMvc.perform(get("/subtask/addsubtask/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

//    @Test
//    void shouldShowSubTasks_WhenUserIsLoggedIn() throws Exception{
//        // Arrange: Vi simulerer en bruger, der er logget ind,
//        // og fortæller mock-service, at den skal returnere en tom liste.
//
//        when(subTaskService.getSubTasksByTaskId(1)).thenReturn(List.of());
//
//        // Act + Assert: Vi kalder subtask-endpointet og tjekker view + model.
//        mockMvc.perform(get("/subtask/1")
//                .sessionAttr("userId", 1))
//                .andExpect(status().isOk())
//                .andExpect(view().name("subtask"))
//                .andExpect(model().attributeExists("subtasks"));
//
//        // Assert: Vi tjekker, at service bliver kaldt med taskId.
//        verify(subTaskService).getSubTasksByTaskId(1);
//
//    }

    @Test
    void shouldSaveSubTask_WhenUserIsLoggedIn() throws Exception{
        // Arrange: Vi simulerer login og sender subtask-data.

        // Act + Assert: Vi kalder save-endpointet og tjekker redirect til task-siden.
        mockMvc.perform(post("/subtask/save")
                .sessionAttr("userId", 1)
                .param("taskId", "1")
                .param("name", "Test subTask")
                .param("description", "Test beskrivelse")
                .param("estimatedHours", "5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/task/1"));

        // Assert: Vi tjekker, at service bliver kaldt med et SubTask-objekt.
        verify(subTaskService).createSubTask(any(SubTask.class));
    }

    @Test
    void shouldRedirectToLogin_WhenSaveSubTaskWithoutLogin() throws Exception{
        // Arrange: Vi giver ikke session med, fordi brugeren ikke er logget ind.

        // Act + Assert: Vi kalder save-endpointet og tjekker redirect til login.
        mockMvc.perform(post("/subtask/save")
                .param("taskId", "1")
                .param("name", "Test subTask")
                .param("description", "Test beskrivelse")
                .param("estimatedHours", "5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        // Assert: Vi tjekker, at service ikke bliver kaldt.
        verify(subTaskService, never()).createSubTask(any(SubTask.class));
    }

    @Test
    void shouldShowUpdateSubTaskPage_WhenUserIsLoggedIn() throws Exception{
        // Arrange: Vi laver en fake subtask og fortæller mock-service, hvad den skal returnere.
        SubTask subTask = new SubTask();
        subTask.setId(2);
        subTask.setTaskId(1);
        subTask.setName("Test subtask");
        subTask.setDescription("Test beskrivelse");
        subTask.setStatus(Status.IN_PROGRESS);
        subTask.setEstimatedHours(5);

        when(subTaskService.getSubTaskById(2)).thenReturn(subTask);

        // Act + Assert: Vi kalder update-siden og tjekker view + model.
        mockMvc.perform(get("/subtask/update/1/2")
                        .sessionAttr("userId", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("updatesubtask"))
                .andExpect(model().attributeExists("subtask"))
                .andExpect(model().attributeExists("taskId"));

        // Assert: Vi tjekker, at service bliver kaldt med subtask-id.
        verify(subTaskService).getSubTaskById(2);

    }

    @Test
    void shouldRedirectToLogin_WhenUpdateSubTaskPageWithoutLogin() throws Exception{
        // Arrange: Vi giver ikke session med, fordi brugeren ikke er logget ind.

        // Act + Assert: Vi kalder update-siden og tjekker redirect til login.
        mockMvc.perform(get("/subtask/update/1/2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        // Assert: Vi tjekker, at service ikke bliver kaldt.
        verify(subTaskService, never()).getSubTaskById(2);
    }

    @Test
    void shouldSaveUpdateSubTask_WhenUserIsLoggedIn() throws Exception{
        // Arrange: Vi simulerer login og sender opdateret subtask-data.

        // Act + Assert: Vi kalder saveUpdate-endpointet og tjekker redirect til task-siden.
        mockMvc.perform(post("/subtask/saveUpdate")
                .sessionAttr("userId", 1)
                .param("id", "2")
                .param("taskId", "1")
                .param("name", "Opdateret subTask")
                .param("description", "Ny opdateret subTask beskrivelse")
                .param("estimatedHours", "6"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/task/1"));

        // Assert: Vi tjekker, at service bliver kaldt med et SubTask-objekt.
        verify(subTaskService).updateSubTask(any(SubTask.class));
    }

    @Test
    void shouldRedirectToLogin_WhenSaveUpdateSubTaskWithoutLogin() throws Exception{
        // Arrange: Vi giver ikke session med, fordi brugeren ikke er logget ind.

        // Act + Assert: Vi kalder saveUpdate-endpointet og tjekker redirect til login.
        mockMvc.perform(post("/subtask/saveUpdate")
                        .param("id", "2")
                        .param("taskId", "1")
                        .param("name", "Opdateret subTask")
                        .param("description", "Ny opdateret subTask beskrivelse")
                        .param("estimatedHours", "6"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        // Assert: Vi tjekker, at service ikke bliver kaldt.
        verify(subTaskService, never()).updateSubTask(any(SubTask.class));
    }

    @Test
    void shouldDeleteSubTask_WhenUserIsLoggedIn() throws Exception{
        // Arrange: Vi simulerer en bruger, der er logget ind.

        // Act + Assert: Vi kalder delete-endpointet og tjekker redirect til task-siden.
        mockMvc.perform(post("/subtask/delete/1/2")
                .sessionAttr("userId", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/task/1"));

        // Assert: Vi tjekker, at service bliver kaldt med subtask-id.
        verify(subTaskService).deleteSubTask(2);
    }

    @Test
    void shouldRedirectToLogin_WhenDeleteSubTaskWithoutLogin() throws Exception{
        // Arrange: Vi giver ikke session med, fordi brugeren ikke er logget ind.

        // Act + Assert: Vi kalder delete-endpointet og tjekker redirect til login.
        mockMvc.perform(post("/subtask/delete/1/2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        // Assert: Vi tjekker, at service ikke bliver kaldt.
        verify(subTaskService, never()).deleteSubTask(2);
    }


}
