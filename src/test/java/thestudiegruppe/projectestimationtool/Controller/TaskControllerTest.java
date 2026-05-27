package thestudiegruppe.projectestimationtool.Controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import thestudiegruppe.projectestimationtool.Model.Project;
import thestudiegruppe.projectestimationtool.Model.Status;
import thestudiegruppe.projectestimationtool.Model.SubProject;
import thestudiegruppe.projectestimationtool.Model.Task;
import thestudiegruppe.projectestimationtool.Service.ProjectService;
import thestudiegruppe.projectestimationtool.Service.SubProjectService;
import thestudiegruppe.projectestimationtool.Service.TaskService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @MockitoBean
    private SubProjectService subProjectService;

    @MockitoBean
    private ProjectService projectService;

    @Test
    void shouldShowTask_WhenUserOwnsProject() throws Exception {

        //Arrange
        Task task = new Task();
        task.setId(1);
        task.setSubProjectId(5);
        task.setStatus(Status.IN_PROGRESS);

        SubProject subProject = new SubProject();
        subProject.setProjectId(10);

        Project project = new Project();
        project.setUserId(20);

        when(taskService.findFullTask(1)).thenReturn(task);
        when(subProjectService.findSubProjectById(5)).thenReturn(subProject);
        when(projectService.findProjectById(10)).thenReturn(project);

        //Act + Assert
        mockMvc.perform(get("/task/1")
                        .sessionAttr("userId", 20))
                .andExpect(status().isOk())
                .andExpect(view().name("task"));
    }

    @Test
    void shouldShowAddTaskForm_WhenUserIsLoggedIn() throws Exception {

        //Act + Assert
        mockMvc.perform(get("/task/addtask/2")
                        .sessionAttr("userId", 1)
                        .param("projectId", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("addtask"));
    }

    @Test
    void shouldSaveTask_WhenUserIsLoggedIn() throws Exception{

        //Act + Assert
        mockMvc.perform(post("/task/save")
                        .sessionAttr("userId", 1)
                        .param("projectId", "1")
                        .param("name", "task 1")
                        .param("deadline", "2027-01-01"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/projects/1"));

        //Assert
        verify(taskService).createTask(any(Task.class));
    }

    @Test
    void shouldDeleteTask_WhenUserIsLoggedIn() throws Exception {

        //Arrange
        SubProject subProject = new SubProject();
        subProject.setProjectId(10);

        when(subProjectService.findSubProjectById(1)).thenReturn(subProject);

        //Act + Assert
        mockMvc.perform(post("/task/deleteTask/1/5")
                        .sessionAttr("userId", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/projects/10"));

        //Assert
        verify(taskService).deleteTask(5);
    }

    @Test
    void shouldUpdateTask_WhenUserIsLoggedIn() throws Exception{

        //Act + Assert
        mockMvc.perform(post("/task/update/5")
                .sessionAttr("userId", 10)
                .param("deadline", "2027-01-01"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/task/5"));

        //Assert
        verify(taskService).updateTask(any(Task.class));
    }

    @Test
    void shouldShowUpdateTaskPage_WhenUserIsLoggedIn() throws Exception{

        //Arrange
        Task task = new Task();
        task.setId(5);

        when(taskService.findTaskById(5)).thenReturn(task);

        //Act + Assert
        mockMvc.perform(get("/task/update/5")
                .sessionAttr("userId", 10))
                .andExpect(status().isOk())
                .andExpect(view().name("updatetask"));
    }
}