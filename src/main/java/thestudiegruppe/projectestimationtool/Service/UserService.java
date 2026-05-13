package thestudiegruppe.projectestimationtool.Service;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import thestudiegruppe.projectestimationtool.Exception.EmailAlreadyExistsException;
import thestudiegruppe.projectestimationtool.Exception.InvalidLoginException;
import thestudiegruppe.projectestimationtool.Exception.NotFoundException;
import thestudiegruppe.projectestimationtool.Exception.NotFoundException;
import thestudiegruppe.projectestimationtool.Model.User;
import thestudiegruppe.projectestimationtool.Repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }


    public void signUp(User user) {
        // Tjekker om user er null, før brugeren oprettes.
        // Hvis user er null, kan vi ikke oprette brugeren, og kaster en exception.
        if (user == null) {
            throw new IllegalArgumentException("User må ikke være null");
        }

        // Vi bruger try/catch, fordi vi først skal prøve at køre repository-metoden.
        // Hvis databasen giver en fejl, fanger catch fejlen.
        // Derefter kaster vi vores egen custom exception, som passer bedre til vores program.
        // Vi kan ikke bare skrive throw med det samme,
        // fordi vi ikke ved om der er en fejl, før metoden er prøvet.
        try{
            // Prøver at gemme brugeren i databasen.
            userRepository.signUp(user);
        }catch (DuplicateKeyException e){
            // Hvis email allerede findes, stopper databasen oprettelsen.
            // Vi laver database-fejlen om til vores egen custom exception.
            throw new EmailAlreadyExistsException(user.getEmail());
        }

    }

    // Finder en bruger ud fra email og password.
    public User findUserForLogIn(String email, String password){
        // Vi bruger try/catch, fordi vi først skal prøve at finde brugeren i databasen.
        // Hvis databasen ikke finder en bruger, fanger catch fejlen.
        // Derefter kaster vi vores egen custom exception for forkert login.
        // Vi kan ikke bare skrive throw med det samme,
        // fordi vi kun vil kaste fejl, hvis login faktisk fejler.
        try{
            // Prøver at finde én bruger i databasen.
            return userRepository.findUserForLogIn(email, password);
        }catch (EmptyResultDataAccessException e){
            // Hvis ingen bruger findes, betyder det at login er forkert.
            // Vi laver Spring-fejlen om til vores egen custom exception.
            throw new InvalidLoginException();
        }
    }

    public void deleteUser(int id) {
        userRepository.delete(id);
    }

    public void update(User user) {
        userRepository.update(user);
    }

    // Finder en bruger ud fra id.
    // Kaster custom UserNotFoundException hvis bruger ikke er fundet
    public User findUserById(int id) {
        User user = userRepository.findUserById(id);
        if (user == null){
            throw new NotFoundException("Bruger", id);
        }
        return user;
    }
}