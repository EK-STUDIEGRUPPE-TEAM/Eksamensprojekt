package thestudiegruppe.projectestimationtool.Controller;

import org.springframework.web.bind.annotation.*;
import thestudiegruppe.projectestimationtool.Model.User;
import thestudiegruppe.projectestimationtool.Service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> showAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/add")
    public User createUser(@RequestBody User user) {
        userService.createUser(user);
        return user;
    }

    @PostMapping("/delete/{id}")
    public User delete(@PathVariable int id, @RequestBody User user) {
        userService.delete(user);
        return user;
    }

    @PostMapping("/update/{id}")
    public User update(@PathVariable int id, @RequestBody User user) {
        userService.update(user);
        return user;
    }

    @GetMapping("/{id}")
    public User findUserById(@PathVariable int id) {
        return userService.findUserById(id);
    }
}