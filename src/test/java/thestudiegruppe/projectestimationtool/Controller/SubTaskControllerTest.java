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

@WebMvcTest(SubTaskController.class)
public class SubTaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SubTaskService subTaskService;

    @Test
    void shouldShowAddSubTaskForm_WhenUserIsLoggedIn() throws Exception{

        // Act + Assert
        mockMvc.perform(get("/subtask/addsubtask/1")
                .sessionAttr("userId", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("addsubtask"))
                .andExpect(model().attributeExists("subtask"))
                .andExpect(model().attributeExists("taskId"));
    }

    @Test
    void shouldRedirectToLogin_WhenAddSubTaskWithoutLogin() throws Exception{

        // Act + Assert
        mockMvc.perform(get("/subtask/addsubtask/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }


    @Test
    void shouldSaveSubTask_WhenUserIsLoggedIn() throws Exception{

        // Act + Assert
        mockMvc.perform(post("/subtask/save")
                .sessionAttr("userId", 1)
                .param("taskId", "1")
                .param("name", "Test subTask")
                .param("description", "Test beskrivelse")
                .param("estimatedHours", "5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/task/1"));

        // Assert
        verify(subTaskService).createSubTask(any(SubTask.class));
    }

    @Test
    void shouldRedirectToLogin_WhenSaveSubTaskWithoutLogin() throws Exception{

        // Act + Assert
        mockMvc.perform(post("/subtask/save")
                .param("taskId", "1")
                .param("name", "Test subTask")
                .param("description", "Test beskrivelse")
                .param("estimatedHours", "5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        // Assert
        verify(subTaskService, never()).createSubTask(any(SubTask.class));
    }

    @Test
    void shouldShowUpdateSubTaskPage_WhenUserIsLoggedIn() throws Exception{
        // Arrange
        SubTask subTask = new SubTask();
        subTask.setId(2);
        subTask.setTaskId(1);
        subTask.setName("Test subtask");
        subTask.setDescription("Test beskrivelse");
        subTask.setStatus(Status.IN_PROGRESS);
        subTask.setEstimatedHours(5);

        when(subTaskService.getSubTaskById(2)).thenReturn(subTask);

        // Act + Assert
        mockMvc.perform(get("/subtask/update/1/2")
                        .sessionAttr("userId", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("updatesubtask"))
                .andExpect(model().attributeExists("subtask"))
                .andExpect(model().attributeExists("taskId"));

        // Assert
        verify(subTaskService).getSubTaskById(2);
    }

    @Test
    void shouldRedirectToLogin_WhenUpdateSubTaskPageWithoutLogin() throws Exception{

        // Act + Assert
        mockMvc.perform(get("/subtask/update/1/2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        // Assert
        verify(subTaskService, never()).getSubTaskById(2);
    }

    @Test
    void shouldSaveUpdateSubTask_WhenUserIsLoggedIn() throws Exception{

        // Act + Assert
        mockMvc.perform(post("/subtask/saveUpdate")
                .sessionAttr("userId", 1)
                .param("id", "2")
                .param("taskId", "1")
                .param("name", "Opdateret subTask")
                .param("description", "Ny opdateret subTask beskrivelse")
                .param("estimatedHours", "6"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/task/1"));

        // Assert
        verify(subTaskService).updateSubTask(any(SubTask.class));
    }

    @Test
    void shouldRedirectToLogin_WhenSaveUpdateSubTaskWithoutLogin() throws Exception{

        // Act + Assert
        mockMvc.perform(post("/subtask/saveUpdate")
                        .param("id", "2")
                        .param("taskId", "1")
                        .param("name", "Opdateret subTask")
                        .param("description", "Ny opdateret subTask beskrivelse")
                        .param("estimatedHours", "6"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        // Assert
        verify(subTaskService, never()).updateSubTask(any(SubTask.class));
    }

    @Test
    void shouldDeleteSubTask_WhenUserIsLoggedIn() throws Exception{

        // Act + Assert
        mockMvc.perform(post("/subtask/delete/1/2")
                .sessionAttr("userId", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/task/1"));

        // Assert
        verify(subTaskService).deleteSubTask(2);
    }

    @Test
    void shouldRedirectToLogin_WhenDeleteSubTaskWithoutLogin() throws Exception{

        // Act + Assert
        mockMvc.perform(post("/subtask/delete/1/2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        // Assert
        verify(subTaskService, never()).deleteSubTask(2);
    }
}
