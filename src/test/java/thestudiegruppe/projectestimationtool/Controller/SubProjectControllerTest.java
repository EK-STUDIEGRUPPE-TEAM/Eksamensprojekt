package thestudiegruppe.projectestimationtool.Controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import thestudiegruppe.projectestimationtool.Model.Project;
import thestudiegruppe.projectestimationtool.Model.SubProject;
import thestudiegruppe.projectestimationtool.Model.User;
import thestudiegruppe.projectestimationtool.Service.ProjectService;
import thestudiegruppe.projectestimationtool.Service.SubProjectService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(SubProjectController.class)


class SubProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;
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
    void shouldShowAddSubProjectForm() throws Exception{


        mockMvc.perform(get("/subproject/addsubproject/1")
                        .sessionAttr("userId", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("addsubproject"));



    }

    @Test
    void shouldCreateSubProject() throws Exception {

        Project project = new Project();
        project.setId(1);
        project.setDeadline(LocalDate.of(2027, 1, 1));




        mockMvc.perform(post("/subproject/add/1").sessionAttr("userId", 1)
                        .param("name", "test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/projects/1"));


    }

    @Test
    void shouldUpdateSubProject() throws Exception {

        Project project = new Project();
        project.setId(1);

        SubProject subProject = new SubProject();
        subProject.setId(1);



      when(subProjectService.findSubProjectById(1)).thenReturn(subProject);


        mockMvc.perform(get("/subproject/update/1/1").sessionAttr("userId", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("updatesubproject"));

    }

    @Test
    void shouldSaveUpdate() throws Exception{

SubProject subProject = new SubProject();


        mockMvc.perform(post("/subproject/saveUpdate").sessionAttr("userId", 1)
                        .param("name", "test").param("projectId", "1"))

                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/projects/1"));


    }

    @Test
    void shouldDeleteSubProject() throws Exception{



        SubProject subProject = new SubProject();
        subProject.setProjectId(1);
        subProject.setId(1);


        when(subProjectService.findSubProjectById(1)).
                thenReturn(subProject);

        mockMvc.perform(post("/subproject/delete/1/1").sessionAttr("userId", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/projects/1"));





    }
}