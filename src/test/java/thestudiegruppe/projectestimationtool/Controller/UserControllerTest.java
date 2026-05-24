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

// Tester kun UserController og web-laget, ikke service/repository/database.
@WebMvcTest(UserController.class)
public class UserControllerTest {

    // Bruges til at sende fake HTTP-requests til controlleren.
    @Autowired
    private MockMvc mockMvc;

    // UserService mockes, fordi vi kun tester controllerens flow.
    @MockitoBean
    private UserService userService;


    @Test
    void shouldSignUpUser() throws Exception{
        //Arrange: Vi sender brugerData som form-parametre.

        // Act + Assert: Vi kalder signup-endpointet og tjekker, at brugeren sendes til login.
        // param bruges, fordi signup-data kommer fra en formular.
        mockMvc.perform(post("/user/signup")
                .param("name", "Test")
                .param("email", "test@mail.dk")
                .param("password", "test123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        // Assert: Vi tjekker, at controlleren kalder signUp-metoden i service-laget.
        verify(userService).signUp(any(User.class));
    }


    @Test
    void shouldLogUserOut() throws Exception{
        // Arrange: Vi laver en fake session, som om brugeren er logget ind.

        // Act: Vi sender en POST-request til logout.
        // Assert: Vi tjekker, at brugeren redirectes til login.
        // Du bruger sessionAttr, når controlleren skal bruge noget, der allerede ligger i sessionen.
        mockMvc.perform(post("/user/logout")
                .sessionAttr("userId", 1)
                .sessionAttr("userName", "Test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("/login"));


    }


    @Test
    void shouldRedirectToLogin_WhenUserIsNotLoggedInAndTriesToSeeProfile() throws Exception{
        // Arrange: Vi giver ikke session med, fordi brugeren ikke er logget ind.

        // Act + Assert: Vi kalder profile-endpointet og tjekker, at brugeren sendes til login.
        mockMvc.perform(get("/user/profile"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    void shouldShowProfile_WhenUserIsLoggedIn() throws Exception{
        // Arrange: Vi laver en fake user og fortæller mock-service, hvad den skal returnere.
        User user = new User();
        user.setId(1);
        user.setName("Test");
        user.setEmail("test@mail.dk");
        user.setPassword("123");

        when(userService.findUserById(1)).thenReturn(user);

         // Act + Assert: Vi kalder profile-endpointet med session og tjekker profile-viewet.
        mockMvc.perform(get("/user/profile")
                .sessionAttr("userId", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("profile"))
                .andExpect(model().attributeExists("user"));

        // Assert: Vi tjekker, at service bliver kaldt med userId.
        verify(userService).findUserById(1);

    }

    @Test
    void shouldLoginUser() throws Exception {
        // Arrange: Vi laver en fake user og fortæller mock-service, hvad den skal returnere.
        User user = new User();
        user.setId(1);
        user.setName("Test");
        user.setEmail("test@mail.dk");
        user.setPassword("123");

        when(userService.findUserForLogIn("test@mail.dk", "123")).thenReturn(user);

        // Act + Assert: Vi kalder login-endpointet og tjekker redirect + session.
        mockMvc.perform(post("/user/login")
                .param("email", "test@mail.dk")
                .param("password", "123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard"))
                .andExpect(request().sessionAttribute("userId", 1))
                .andExpect(request().sessionAttribute("userName", "Test"));

        // Assert: Vi tjekker, at service bliver kaldt med email og password.
        verify(userService).findUserForLogIn("test@mail.dk", "123");
    }


    @Test
    void shouldDeleteUser_WhenUserIsLoggedIn() throws Exception{
        // Arrange: Vi simulerer en bruger, der er logget ind.

        // Act + Assert: Vi kalder delete-endpointet og tjekker redirect til signup.
        mockMvc.perform(post("/user/delete/1")
                .sessionAttr("userId", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/signup"));

        // Assert: Vi tjekker, at service bliver kaldt med userId.
        verify(userService).deleteUser(1);
    }

    @Test
    void shouldRedirectToLogin_WhenDeleteUserWithoutLogin() throws Exception{
        // Arrange: Vi giver ikke session med, fordi brugeren ikke er logget ind.

        // Act + Assert: Vi kalder delete-endpointet og tjekker redirect til login.
        mockMvc.perform(post("/user/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        // Assert: Vi tjekker, at service ikke bliver kaldt.
        verify(userService, never()).deleteUser(1);
    }

    @Test
    void shouldUpdateUser_WhenUserIsLoggedIn() throws Exception{
        // Arrange: Vi simulerer en logget ind bruger og sender nye brugerdata.

        // Act + Assert: Vi kalder update-endpointet og tjekker redirect til profile.
        mockMvc.perform(post("/user/update/1")
                .sessionAttr("userId", 1)
                .param("name", "Test")
                .param("email", "test@mail.dk")
                .param("password", "123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/profile"));

        // Assert: Vi tjekker, at service bliver kaldt med et User-objekt.
        verify(userService).update(any(User.class));
    }

    @Test
    void shouldRedirectToLogin_WhenUpdateUserWithoutLogin() throws Exception{
        // Arrange: Vi giver ikke session med, fordi brugeren ikke er logget ind.

        // Act + Assert: Vi kalder update-endpointet og tjekker redirect til login.
        mockMvc.perform(post("/user/update/1")
                        .param("name", "Test")
                        .param("email", "test@mail.dk")
                        .param("password", "123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        // Assert: Vi tjekker, at service ikke bliver kaldt.
        verify(userService, never()).update(any(User.class));
    }


}
