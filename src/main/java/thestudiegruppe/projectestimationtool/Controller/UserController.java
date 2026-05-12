package thestudiegruppe.projectestimationtool.Controller;
import org.springframework.web.bind.annotation.*;
import thestudiegruppe.projectestimationtool.Model.User;
import thestudiegruppe.projectestimationtool.Service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public User showAllUsers(User user){
        userService.getAllUsers(user);
        return user;
    }


    @PostMapping("/add")
    public User createUser(User user){
        userService.createUser(user);
        return user;
    }

    @PostMapping("/delete")
    public User delete(@RequestBody User user){
        userService.delete(user);
        return user;
    }

//    @PostMapping("/save")
//    public String save(User user){
//
//        return null;
//    }

    @PostMapping("/update")
    public User update(User user) {
        userService.update(user);
        return user;
    }

    @GetMapping("/{id}")
    public User findUserById(@PathVariable int id) {
        return userService.findUserById(id);
    }
}
