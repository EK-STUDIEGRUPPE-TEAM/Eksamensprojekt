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

    public List<User> getAllUsers(User user) {
        userRepository.getAllUsers();
        return getAllUsers(user);
    }
    public void createUser(User user){
userRepository.createUser(user);
    }

    public void delete(User user){
userRepository.delete(user.getId());
    }

    public void update(User user){
userRepository.update(user);
    }

    public User findUserById(int id){
        return userRepository.findUserById(id);
    }
}
