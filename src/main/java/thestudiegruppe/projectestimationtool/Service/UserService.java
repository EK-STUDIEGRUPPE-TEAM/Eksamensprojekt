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

        if (user == null) {
            throw new IllegalArgumentException("User må ikke være null");
        }

        try{
            userRepository.signUp(user);

        }catch (DuplicateKeyException e){
            throw new EmailAlreadyExistsException(user.getEmail());
        }
    }

    public User findUserForLogIn(String email, String password){

        try{
            return userRepository.findUserForLogIn(email, password);

        }catch (EmptyResultDataAccessException e){
            throw new InvalidLoginException();
        }
    }

    public void deleteUser(int id) {
        userRepository.delete(id);
    }

    public void update(User user) {
        userRepository.update(user);
    }


    public User findUserById(int id) {

        try {
            return userRepository.findUserById(id);

        } catch (EmptyResultDataAccessException e){
            throw new NotFoundException("Brugeren", id);
        }
    }
}