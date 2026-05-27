package thestudiegruppe.projectestimationtool.Service.Unittest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import thestudiegruppe.projectestimationtool.Exception.EmailAlreadyExistsException;
import thestudiegruppe.projectestimationtool.Exception.InvalidLoginException;
import thestudiegruppe.projectestimationtool.Exception.NotFoundException;
import thestudiegruppe.projectestimationtool.Model.User;
import thestudiegruppe.projectestimationtool.Repository.UserRepository;
import thestudiegruppe.projectestimationtool.Service.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void getAllUsers_shouldReturnListOfUsers() {

        //Arrange
        User user1 = new User();
        User user2 = new User();


        List<User> users = List.of(user1, user2);

        when(userRepository.getAllUsers()).thenReturn(users);

        //Act
        List<User> result = userService.getAllUsers();

        //Assert
        assertEquals(users, result);

       //Assert
        verify(userRepository, times(1)).getAllUsers();

    }


    @Test
    void signUp_shouldCallRepositorySignUp_whenUserIsNotNull() {

        //Arrange
        User user = new User();

        //Act
        userService.signUp(user);

        //Assert
        verify(userRepository, times(1)).signUp(user);
    }


    @Test
    void signUp_shouldThrowException_whenUserIsNull() {

        //Arrange
        User user = null;

        //Act + Assert
        assertThrows(IllegalArgumentException.class, () -> userService.signUp(user));

       //Assert
        verify(userRepository, never()).signUp(any(User.class));
    }


    @Test
    void signUp_shouldThrowException_whenUserEmailAlreadyExists(){

        //Arrange
        User user = new User(null, "Jakob", "jkob@gmail.com", "J_123", null);

        doThrow(new DuplicateKeyException("Email findes allerede")).when(userRepository).signUp(user);

       //Act + Assert
        assertThrows(EmailAlreadyExistsException.class, () -> userService.signUp(user));

       //Assert
        verify(userRepository, times(1)).signUp(user);
    }


    @Test
    void findUserForLogIn_shouldReturnUser_whenLogInIsValid(){

        //Arrange
        String email = "test@gmail.com";
        String password = "test123";

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        when(userRepository.findUserForLogIn(email, password)).thenReturn(user);

        //Act
        User result = userService.findUserForLogIn(email, password);

        //Assert
        assertEquals(user, result);

        //Assert
        verify(userRepository, times(1)).findUserForLogIn(email, password);

    }


    @Test
    void findUserForLogIn_shouldThrowException_whenLoginIsInvalid(){

        //Arrange
        String email = "wrongEmail@gmail.com";
        String password = "wrongPassword123";

        when(userRepository.findUserForLogIn(email,password)).thenThrow(new EmptyResultDataAccessException(1));

        assertThrows(InvalidLoginException.class, () -> userService.findUserForLogIn(email, password));

        //Assert
        verify(userRepository, times(1)).findUserForLogIn(email, password);
    }


    @Test
    void deleteUser_shouldCallRepositoryDelete() {

        //Arrange
        User user = new User();
        user.setId(1);

        //Act
        userService.deleteUser(user.getId());

        //Assert
        verify(userRepository, times(1)).delete(1);
    }

    @Test
    void update_shouldCallRepositoryUpdate() {

        //Arrange
        User user = new User();
        //Act
        userService.update(user);

        //Assert
        verify(userRepository, times(1)).update(user);
    }

    @Test
    void findUserById_shouldReturnUser_whenUserExists() {

        //Arrange
        User user = new User();
        int userId = 1;

        when(userRepository.findUserById(userId)).thenReturn(user);

        //Act
        User result = userService.findUserById(userId);

        //Assert
        assertEquals(user, result);

        //Assert
        verify(userRepository, times(1)).findUserById(userId);

    }

    @Test
    void findUserById_shouldThrowException_whenUserDoesNotExist(){

        //Arrange
        int userId = 1;

        when(userRepository.findUserById(userId)).thenThrow(new EmptyResultDataAccessException(1));

        //Act
        assertThrows(NotFoundException.class, () -> userService.findUserById(userId));

        //Assert
        verify(userRepository, times(1)).findUserById(userId);
    }
}