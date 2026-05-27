package thestudiegruppe.projectestimationtool.Controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import thestudiegruppe.projectestimationtool.Model.Project;
import thestudiegruppe.projectestimationtool.Model.User;
import thestudiegruppe.projectestimationtool.Service.ProjectService;
import thestudiegruppe.projectestimationtool.Service.UserService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PageController.class)
public class PageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProjectService projectService;

    @MockitoBean
    private UserService userService;

    @Test
    void shouldShowIndexPage() throws Exception{
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void shouldShowDashboard() throws Exception{
        //Arrange
        User user = new User();
        Project project = new Project();
        project.setUserId(1);
        List<Project> projects = List.of(project);

        when(projectService.findProjectByUserId(1)).thenReturn(projects);
        when(userService.findUserById(1)).thenReturn(user);

        //Act + Assert
        mockMvc.perform(get("/dashboard")
                .sessionAttr("userId", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard"));
    }

    @Test
    void shouldShowLoginPage() throws  Exception{

        //Act + Assert
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    void shouldShowSignupPage() throws Exception{

        //Act + Assert
        mockMvc.perform(get("/signup"))
                .andExpect(status().isOk())
                .andExpect(view().name("signup"))
                .andExpect(model().attributeExists("user"));
    }
}
