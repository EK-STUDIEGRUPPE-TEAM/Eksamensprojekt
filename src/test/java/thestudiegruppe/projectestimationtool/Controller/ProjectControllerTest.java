package thestudiegruppe.projectestimationtool.Controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import thestudiegruppe.projectestimationtool.Model.Project;
import thestudiegruppe.projectestimationtool.Service.ProjectService;

import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjectController.class)


class ProjectControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void shouldShowProjects() throws Exception {
        mockMvc.perform(get("/projects").sessionAttr("userId",1))
                .andExpect(status().isOk())
                .andExpect(view().name("projects"));
    }


    @Test
    void shouldShowProject() throws Exception {


        Project project = new Project();

        project.setId(1);
        project.setUserId(1);

        when(projectService.findFullProject(1))
                .thenReturn(project);

        mockMvc.perform(get("/projects/1")
                        .sessionAttr("userId", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("project"));
    }

    @Test
    void shouldShowAddProjectForm() throws Exception{


        mockMvc.perform(get("/projects/addproject")
                        .sessionAttr("userId", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("addproject"));

    }

    @Test
    void shouldAddProject() throws Exception{

        Project project = new Project();
        project.setUserId(1);
        project.setDeadline(LocalDate.of(2027, 1, 1));


        mockMvc.perform(post("/projects/save").sessionAttr("userId", 1)
                        .param("name", "test")
                        .param("deadline", "2026-05-28")
                        .param("budget", "10000"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/projects"));

    }

    @Test
    void shouldDeleteProject() throws Exception {

        Project project = new Project();
        project.setUserId(1);
        project.setId(1);
        project.setDeadline(LocalDate.of(2027, 1, 1));

        when(projectService.findProjectById(1)).
                thenReturn(project);

        mockMvc.perform(get("/projects/delete/1").sessionAttr("userId", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/projects"));



    }

    @Test
    void shouldShowUpdatePage() throws Exception {

        Project project = new Project();
        project.setUserId(1);
        project.setId(1);
        project.setDeadline(LocalDate.of(2027, 1, 1));

        when(projectService.findProjectById(1)).
                thenReturn(project);

        mockMvc.perform(get("/projects/update/1").sessionAttr("userId", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("updateproject"));

    }

    @Test
    void shouldUpdateProject() throws Exception{

        Project project = new Project();
        project.setUserId(1);
        project.setId(1);
        project.setDeadline(LocalDate.of(2027, 1, 1));

        when(projectService.findProjectById(1)).
                thenReturn(project);

        mockMvc.perform(post("/projects/update/1").sessionAttr("userId", 1)
                .param("name", "test")
                .param("deadline", "2026-05-28")
                .param("budget", "10000"))

                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/projects"));


    }
}