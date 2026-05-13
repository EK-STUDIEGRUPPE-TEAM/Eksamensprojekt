package thestudiegruppe.projectestimationtool.Service;

import org.springframework.stereotype.Service;
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

    // Tjekker om user er null, før brugeren oprettes.
    // Hvis user er null, kastes en midlertidig standard exception, som vil ændres senere.
    // Denne kan senere erstattes af en custom exception.
    public void createUser(User user) {
        if (user == null){
            throw new IllegalArgumentException("User må ikke være null");
        }
        userRepository.createUser(user);

    }

    public void deleteUser(int id) {
        userRepository.delete(id);
    }

    public void update(User user) {
        userRepository.update(user);
    }

    // Finder en bruger ud fra id.
    // Hvis ingen bruger findes, kastes en midlertidig standard exception, som vil ændres senere.
    // Denne kan senere erstattes af en custom exception.
    public User findUserById(int id) {
        User user = userRepository.findUserById(id);
        if (user == null){
            throw new RuntimeException("Bruger med " + id + " blev ikke fundet");
        }
        return user;
    }
}