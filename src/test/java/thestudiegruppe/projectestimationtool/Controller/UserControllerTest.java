package thestudiegruppe.projectestimationtool.Controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import thestudiegruppe.projectestimationtool.Model.User;
import thestudiegruppe.projectestimationtool.Service.UserService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;


    @Test
    void shouldSignUpUser_WhenFormIsValid() throws Exception{

        //Act + Assert
        mockMvc.perform(post("/user/signup")
                .param("name", "Test")
                .param("email", "test@mail.dk")
                .param("password", "test123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        //Assert
        verify(userService).signUp(any(User.class));
    }


    @Test
    void shouldLogUserOut_WhenUserIsLoggedIn() throws Exception{

        //Act + Assert
        mockMvc.perform(post("/user/logout")
                .sessionAttr("userId", 1)
                .sessionAttr("userName", "Test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }


    @Test
    void shouldRedirectToLogin_WhenUserIsNotLoggedInAndTriesToSeeProfile() throws Exception{

        //Act + Assert
        mockMvc.perform(get("/user/profile"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    void shouldShowProfile_WhenUserIsLoggedIn() throws Exception{
        //Arrange
        User user = new User();
        user.setId(1);
        user.setName("Test");
        user.setEmail("test@mail.dk");
        user.setPassword("123");

        when(userService.findUserById(1)).thenReturn(user);

        //Act + Assert
        mockMvc.perform(get("/user/profile")
                .sessionAttr("userId", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("profile"))
                .andExpect(model().attributeExists("user"));

        //Assert
        verify(userService).findUserById(1);

    }

    @Test
    void shouldLoginUser_WhenInputAreValid() throws Exception {
        //Arrange
        User user = new User();
        user.setId(1);
        user.setName("Test");
        user.setEmail("test@mail.dk");
        user.setPassword("123");

        when(userService.findUserForLogIn("test@mail.dk", "123")).thenReturn(user);

        //Act + Assert
        mockMvc.perform(post("/user/login")
                .param("email", "test@mail.dk")
                .param("password", "123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard"))
                .andExpect(request().sessionAttribute("userId", 1)
                );

        //Assert
        verify(userService).findUserForLogIn("test@mail.dk", "123");
    }


    @Test
    void shouldDeleteUser_WhenUserIsLoggedIn() throws Exception{

        //Act + Assert
        mockMvc.perform(post("/user/delete/1")
                .sessionAttr("userId", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/signup"));

       //Assert
        verify(userService).deleteUser(1);
    }

    @Test
    void shouldRedirectToLogin_WhenDeleteUserWithoutLogin() throws Exception{

        //Act + Assert
        mockMvc.perform(post("/user/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        //Assert
        verify(userService, never()).deleteUser(1);
    }

    @Test
    void shouldUpdateUser_WhenUserIsLoggedIn() throws Exception{

        //Act + Assert
        mockMvc.perform(post("/user/update/1")
                .sessionAttr("userId", 1)
                .param("name", "Test")
                .param("email", "test@mail.dk")
                .param("password", "123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard"));

        //Assert
        verify(userService).update(any(User.class));
    }

    @Test
    void shouldRedirectToLogin_WhenUpdateUserWithoutLogin() throws Exception{

        //Act + Assert
        mockMvc.perform(post("/user/update/1")
                        .param("name", "Test")
                        .param("email", "test@mail.dk")
                        .param("password", "123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

       //Assert
        verify(userService, never()).update(any(User.class));
    }
}
