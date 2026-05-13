package thestudiegruppe.projectestimationtool.Service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import thestudiegruppe.projectestimationtool.Exception.UserNotFoundException;
import thestudiegruppe.projectestimationtool.Model.User;
import thestudiegruppe.projectestimationtool.Repository.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/* Den her annotation gør at Mockito virker i testen.
Uden den kan @Mock og @InjectMocks ikke rigtigt arbejde ordentligt
og derfor hjælper dem med det. */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    //Lav en falsk version af UserRepository.
    @Mock
    private UserRepository userRepository;

    /* Lav en rigtig UserService, og sæt den falske userRepository ind i den.
     Så man kan teste service-klassen, men uden en rigtig database. */
    @InjectMocks
    private UserService userService;


    @Test
    void getAllUsers_shouldReturnListOfUsers() {
        //Arrange: Her laver vi vores testdata.
        User user1 = new User();
        User user2 = new User();

        /* Vi lægger brugerne i en List.of(),
        som laver en liste med de objekter, vi giver den. */
        List<User> users = List.of(user1, user2);

        /* Vi fortæller mock-repository, at den skal returnere users-listen,
        når getAllUsers() bliver kaldt. */
        when(userRepository.getAllUsers()).thenReturn(users);

        //Act: Vi kalder service-metoden, som vi vil teste.
        List<User> result = userService.getAllUsers();

        /* Assert: Vi tjekker om vi får den forventede liste tilbage.
        assertEquals() sammenligner forventet værdi med faktisk værdi. */
        assertEquals(users, result);

        // Vi tjekker også at repository-metoden blev kaldt 1 gang.
        verify(userRepository, times(1)).getAllUsers();

    }

    @Test
    void createUser_shouldCallRepositoryCreateAUser_whenUserIsNotNull() {
        //Arrange: Lave testdata.
        User user = new User();

        //Act: Vi kalder metoden i service-klassen, som vi vil teste.
        userService.createUser(user);

        /* Assert:Vi tjekker om repository-metoden
        blev kaldt præcis 1 gang med den user, vi sendte ind.

        verify(...) bruges til at kontrollere, om en mock-metode er blevet kaldt.*/
        verify(userRepository, times(1)).createUser(user);
    }

    @Test
    void createUser_shouldThrowException_whenUserIsNull() {
        //Arrange: Vi laver testData.
        User user = null;

        /*Act + Assert: Vi forventer, at metoden kaster en IllegalArgumentException,
        når vi prøver at oprette en null-user.

        assertThrows(...) bruges til at teste, at en exception bliver kastet.*/
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(user));

        /* Assert: Vi tjekker at repository aldrig blev kaldt.
         never() betyder, at metoden ikke må være kørt en eneste gang.
         any(User.class) betyder "en hvilken som helst User". */
        verify(userRepository, never()).createUser(any(User.class));

    }


    @Test
    void deleteUser_shouldCallRepositoryDelete() {
        //Arrange: Vi laver testdata.
        User user = new User();
        user.setId(1);
        //Act: Vi kalder metoden som vi vil teste.
        userService.deleteUser(user.getId());

        //Assert: Vi tjekker om userRepository.delete() blev kaldt præcis 1 gang
        verify(userRepository, times(1)).delete(1);
    }

    @Test
    void update_shouldCallRepositoryUpdate() {
        //Arrange: Vi laver et objekt, som kan sendes ind.
        User user = new User();
        //Act: Vi kalder metoden.
        userService.update(user);
        //Assert: Vi tjekker om repository fik kaldet.
        verify(userRepository, times(1)).update(user);
    }

    @Test
    void findUserById_shouldReturnUser_whenUserExists() {
        //Arrange: Her opretter vi en User og et id, som skal bruges i testen.
        User user = new User();
        int userId = 1;

        /*Vi bestemmer hvad mock-repository skal returnere,
        når findUserById(userId) bliver kaldt.

        when().thenReturn() betyder: "når denne metode bliver kaldt, så returnér denne user".
         */
        when(userRepository.findUserById(userId)).thenReturn(user);

        /* Act: Vi kalder metoden i service-klassen,
        og gemmer resultatet i variablen result. */
        User result = userService.findUserById(userId);

        /* Assert: vi tjekker om resultatet fra service er den samme user,
        som i repository returnerede.
         assertEquals() sammenligner forventet værdi med faktisk værdi.*/
        assertEquals(user, result);

        //Vi tjekker om repository-metoden blev kaldt præcis 1 gang.
        verify(userRepository, times(1)).findUserById(userId);

    }

    @Test
    void findUserById_shouldThrowUserNotFoundException_whenUserDoesNotExist(){
        //Arrange: Laver testData.
        int userId = 1;

        /* Vi bestemmer at mock-repository skal returnere null,
        når findUserById(userId) bliver kaldt.
        Det simulerer situationen, hvor brugeren ikke findes.
         */
        when(userRepository.findUserById(userId)).thenReturn(null);

        /*Act: Vi forventer, at metoden kaster en custom UserNotFoundException,
        når repository ikke finder nogen user.
        assertThrows(...) bruges til at teste, at en exception bliver kastet.
         */
        assertThrows(UserNotFoundException.class, () -> userService.findUserById(userId));

        //Vi tjekker at repository-metoden blev kaldt 1 gang.
        verify(userRepository, times(1)).findUserById(userId);
    }
}