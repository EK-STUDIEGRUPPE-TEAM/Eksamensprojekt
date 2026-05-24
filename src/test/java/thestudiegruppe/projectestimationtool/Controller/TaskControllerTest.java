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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void shouldShowTask() throws Exception {

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

        mockMvc.perform(get("/task/1").sessionAttr("userId", 20))
                .andExpect(status().isOk())
                .andExpect((view().name("task")));
    }

    @Test
    void addTask() {
    }

    @Test
    void save() {
    }

    @Test
    void delete() {
    }

    @Test
    void update() {
    }

    @Test
    void showUpdateTask() {
    }
}