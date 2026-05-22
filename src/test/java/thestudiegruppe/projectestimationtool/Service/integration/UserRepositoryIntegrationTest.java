package thestudiegruppe.projectestimationtool.Service.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import thestudiegruppe.projectestimationtool.Model.User;
import thestudiegruppe.projectestimationtool.Repository.UserRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;


@SpringBootTest

// @ActiveProfiles("test") gør, at testen bruger application-test.properties.
// Derfor bruger testen H2-databasen i stedet for den normale database.
@ActiveProfiles("test")

// @Sql kører h2init.sql før hver testmetode.
// Det gør, at databasen starter med de samme testdata hver gang.
@Sql(scripts = "classpath:h2init.sql", executionPhase = BEFORE_TEST_METHOD)
public class UserRepositoryIntegrationTest {

    // @Autowired får Spring til at indsætte UserRepository automatisk.
    // Så vi kan bruge den rigtige repository i testen uden at skrive new.
    @Autowired
    private UserRepository userRepository;


    @Test
    void getAllUsers_ShouldReturnUsersFromDatabase(){
        //Arrange: TestData er allerede lavet inde i H2init.sql.


        //Act: Kalder metoden der henter alle users fra databasen.
        List<User> users = userRepository.getAllUsers();

        //Assert: Tjekker at der kommer 2 users tilbage fra testdatabasen.
        assertThat(users)
                .extracting(User::getName)
                .contains("Test", "Abbas");

    }

    @Test
    void signUp_shouldSaveNewUserInDatabase(){
        //Arrange: Lav testData.
        User user = new User();
        user.setName("test");
        user.setEmail("test123@mail.dk");
        user.setPassword("123");

        //Act: Kalder metoden der gemmer en ny bruger.
        userRepository.signUp(user);

        //Assert: Henter alle users fra databasen og tjekker om den nye user er gemt.
        List<User> users = userRepository.getAllUsers();

        assertThat(users)
                .extracting(User::getEmail)// Vi bruger extracting(User::getEmail) for kun at teste emails i listen. :: betyder, at getEmail kaldes på hver User.
                .contains("test123@mail.dk");

    }

    @Test
    void findUserForLogIn(){
        //Arrange: Vi bruger email og password fra en user, der allerede findes i h2init.sql.
        String email = "test@mail.com";
        String password = "1234";

        //Act: Vi kalder login-metoden og gemmer den User, som databasen returnerer.
        User foundUser = userRepository.findUserForLogIn(email, password);

        //Assert:Vi tjekker, at den fundne user har den samme email og password, som vi søgte efter.
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo(email);
        assertThat(foundUser.getPassword()).isEqualTo(password);
    }

    @Test
    void delete(){
        //Arrange: Vi vælger et userId, som allerede findes i h2init.sql.
        int userId = 1;

        //Act: Vi kalder delete-metoden, som skal slette useren fra H2-databasen.
        userRepository.delete(userId);

        //Assert: Vi henter alle users fra databasen efter delete.
        List<User> users = userRepository.getAllUsers();

        //Assert: Vi tjekker, at den slettede users id ikke længere findes i databasen.
        assertThat(users)
                .extracting(User::getId)
                .doesNotContain(userId);
    }

    @Test
    void findUserById(){
        //Arrange: Vi vælger et userId, som allerede findes i h2init.sql.
        int userId = 1;

        //Act: Vi kalder findUserById-metoden og gemmer den User, som databasen returnerer.
        User foundUser = userRepository.findUserById(userId);

        //Assert: Vi tjekker, at metoden faktisk har fundet en user.
        assertThat(foundUser).isNotNull();

        // Assert: Vi tjekker, at den fundne users id matcher det id, som vi søgte efter.
        assertThat(foundUser.getId()).isEqualTo(userId);
    }

    @Test
    void update(){
        //Arrange: Vi vælger en user, som allerede findes i h2init.sql.
        int userId = 1;

        // Arrange: Vi laver et User-objekt med samme id, men med de nye værdier, som databasen skal opdateres med.

        //User user = new User();

        User user = userRepository.findUserById(userId);
        //user.setId(userId);
        user.setName("test12");
        user.setPassword("12");

        // Act: Vi kalder update-metoden, som skal ændre userens name og password i H2-databasen.
        userRepository.update(user);


        // Assert: Vi henter useren fra databasen igen efter update.
        User updatedUser = userRepository.findUserById(userId);

        // Assert: Vi tjekker, at useren stadig findes.
        assertThat(updatedUser).isNotNull();

        // Assert: Vi tjekker, at databasen nu indeholder de nye værdier.
        assertThat(updatedUser.getId()).isEqualTo(userId);
        assertThat(updatedUser.getName()).isEqualTo("test12");
        assertThat(updatedUser.getPassword()).isEqualTo("12");

    }




}
