package thestudiegruppe.projectestimationtool.Repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import thestudiegruppe.projectestimationtool.Model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;


@SpringBootTest

@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = BEFORE_TEST_METHOD)
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void getAllUsers_ShouldReturnUsersFromDatabase(){

        //Act
        List<User> users = userRepository.getAllUsers();

        //Assert
        assertThat(users)
                .hasSize(2);

        //Assert
        assertThat(users)
                .extracting(User::getName)
                .contains("Test", "Abbas");
    }

    @Test
    void signUp_shouldSaveNewUserInDatabase(){

        //Arrange
        User user = new User();
        user.setName("test");
        user.setEmail("test123@mail.dk");
        user.setPassword("123");

        //Act
        userRepository.signUp(user);

        //Assert
        List<User> users = userRepository.getAllUsers();

        assertThat(users)
                .extracting(User::getEmail)
                .contains("test123@mail.dk");

    }

    @Test
    void findUserForLogIn_ShouldReturnUser_WhenEmailAndPasswordMatch(){

        //Arrange
        String email = "test@mail.com";
        String password = "1234";

        //Act
        User foundUser = userRepository.findUserForLogIn(email, password);

        //Assert
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo(email);
        assertThat(foundUser.getPassword()).isEqualTo(password);
    }

    @Test
    void delete_ShouldDeleteUserFromDatabase(){

        //Arrange
        int userId = 1;

        //Act
        userRepository.delete(userId);

        //Assert
        List<User> users = userRepository.getAllUsers();

        //Assert
        assertThat(users)
                .extracting(User::getId)
                .doesNotContain(userId);
    }

    @Test
    void findUserById_ShouldReturnUser_WhenUserExists(){

        //Arrange
        int userId = 1;

        //Act
        User foundUser = userRepository.findUserById(userId);

        //Assert
        assertThat(foundUser).isNotNull();

        // Assert
        assertThat(foundUser.getId()).isEqualTo(userId);
    }

    @Test
    void update_ShouldUpdateUserInDatabase(){

        //Arrange
        int userId = 1;

        User user = userRepository.findUserById(userId);
        user.setName("test12");
        user.setPassword("12");

        //Act
        userRepository.update(user);

        //Assert
        User updatedUser = userRepository.findUserById(userId);

        //Assert
        assertThat(updatedUser).isNotNull();

        //Assert
        assertThat(updatedUser.getId()).isEqualTo(userId);
        assertThat(updatedUser.getName()).isEqualTo("test12");
        assertThat(updatedUser.getPassword()).isEqualTo("12");

    }
}
